package ar.edu.unq.desapp.grupoI.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoI.backenddesappapi.exceptions.InvalidTransactionAction
import ar.edu.unq.desapp.grupoI.backenddesappapi.exceptions.TransactionNotFoundException
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.*
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Action
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Operation
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.OperationState
import ar.edu.unq.desapp.grupoI.backenddesappapi.persistence.repository.IntentionRepository
import ar.edu.unq.desapp.grupoI.backenddesappapi.persistence.repository.TransactionRepository
import ar.edu.unq.desapp.grupoI.backenddesappapi.persistence.repository.UserRepository
import ar.edu.unq.desapp.grupoI.backenddesappapi.service.CryptoCurrencyService
import ar.edu.unq.desapp.grupoI.backenddesappapi.service.IntentionService
import ar.edu.unq.desapp.grupoI.backenddesappapi.service.TransactionService
import ar.edu.unq.desapp.grupoI.backenddesappapi.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestTemplate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale
import kotlin.jvm.optionals.getOrNull

@Service
@Transactional
class TransactionServiceImpl: TransactionService {

    @Autowired lateinit var userService: UserService
    @Autowired lateinit var intentionService: IntentionService
    @Autowired lateinit var cryptoCurrencyService: CryptoCurrencyService
    @Autowired lateinit var transactionRepository: TransactionRepository
    val restTemplate: RestTemplate = RestTemplate()

    override fun createTransaction(intentionId: Long, interestedUserId: Long): Transaction {
        val intention = intentionService.getIntentionById(intentionId)

        cryptoCurrencyService.validatePrice(intention.cryptoAsset.toString(), intention.price)

        val interestedUser = userService.getUserById(interestedUserId)
        val action = if (intention.operation == Operation.SELL) Action.CONFIRMTRANSFER
                 else Action.TRANSFER

        val transaction = Transaction(
            action = action,
            interestedUser = interestedUser
        )

        transaction.intention = intention
        transactionRepository.save(transaction)

        intention.deactivate()
        intentionService.saveIntention(intention)

        return transaction
    }

    override fun getTransactionById(id: Long): Transaction {
        return transactionRepository.findById(id)
            .getOrNull() ?: throw TransactionNotFoundException("Could not find a transaction with id: `${id}`")
    }

    override fun finishTransaction(transactionId: Long): Transaction {
        val transaction = getTransactionById(transactionId)
        val intentionUser = transaction.intentionUser()
        val interestedUser = transaction.interestedUser
        var reputation = 0

        if (this.isPast30Minutes(transaction.initTime)) reputation = 5
        else reputation = 10

        intentionUser.modifyReputation({ a, b -> a + b }, reputation)
        interestedUser.modifyReputation({ a, b -> a + b }, reputation)

        transaction.changeState(OperationState.INACTIVE)

        userService.saveUser(intentionUser)
        userService.saveUser(interestedUser)

        return transactionRepository.save(transaction)
    }

    override fun cancelTransaction(transactionId: Long, userId: Long): Transaction {
        val transaction = getTransactionById(transactionId)
        val user = userService.getUserById(userId)

        transaction.changeAction(Action.CANCEL)
        transaction.changeState(OperationState.INACTIVE)

        user.modifyReputation({ a, b -> a - b}, 20)

        userService.saveUser(user)

        return transactionRepository.save(transaction)
    }


    override fun isPast30Minutes(initTime: String): Boolean {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        val transactionTime: LocalDateTime = LocalDateTime.parse(initTime, formatter)
        val currentTime = LocalDateTime.now()

        val differenceInMinutes = ChronoUnit.MINUTES.between(transactionTime, currentTime)

        return differenceInMinutes > 30
    }
    override fun getOperatedVolumeFor(userId: Long, startDate: String, endDate: String): OperatedVolume {
        // queda ver como hacer la consulta entre fechas
        //val transactions = transactionRepository.findByUserIdAndDateBetween(userId, startDate, endDate)
        val transactions = transactionRepository.findByInterestedUserId(userId)
        var totalUSD = 0.0
        var totalARS = 0.0
        val operatedAssetsMap = mutableMapOf<String, OperatedAsset>()

        for (transaction in transactions) {
            val intention = transaction.intention
            val price = cryptoCurrencyService.getCurrencyValue(intention.cryptoAsset.toString())!!
            val priceInPesos = price * getDolarAvgPrice()


            val operatedAsset = operatedAssetsMap[intention.cryptoAsset.toString()]
            if (operatedAsset != null) {
                operatedAsset.nominalAmount += intention.amount
                operatedAsset.quoteValueInARS += intention.amount * priceInPesos
            } else {
                operatedAssetsMap[intention.cryptoAsset.toString()] = OperatedAsset(
                    cryptoAsset = intention.cryptoAsset.toString(),
                    nominalAmount = intention.amount,
                    currentQuote = price,
                    quoteValueInARS = intention.amount * priceInPesos
                )
            }

            totalUSD += intention.amount * intention.price
            totalARS += totalUSD * getDolarAvgPrice()
        }
        val operatedAssets = operatedAssetsMap.values.toList()

        return OperatedVolume(
            requestDate = LocalDateTime.now().toString(),
            totalAmountUSD = totalUSD,
            totalAmountPesos = totalARS,
            assets = operatedAssets
        )
    }

    private fun getDolarAvgPrice(): Double {
        val actualDolar = restTemplate.getForObject("https://dolarapi.com/v1/dolares/blue", Dolar::class.java)!!
        return (actualDolar.compra + actualDolar.venta) / 2.0
    }
}
