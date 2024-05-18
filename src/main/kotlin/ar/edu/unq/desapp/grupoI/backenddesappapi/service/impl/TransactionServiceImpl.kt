package ar.edu.unq.desapp.grupoI.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoI.backenddesappapi.exceptions.InvalidTransactionAction
import ar.edu.unq.desapp.grupoI.backenddesappapi.exceptions.TransactionNotFoundException
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.Intention
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.Transaction
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.User
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

        val intentionUser = intention.user!!
        intentionUser.addTransaction(transaction)
        userService.saveUser(intentionUser)

        intention.deactivate()
        intentionService.saveIntention(intention)

        return transaction
    }

    override fun getTransactionById(id: Long): Transaction {
        return transactionRepository.findById(id)
            .getOrNull() ?: throw TransactionNotFoundException("Could not find a transaction with id: `${id}`")
    }

    override fun finishTransaction(transactionId: Long) {
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
        transactionRepository.save(transaction)
    }

    override fun cancelTransaction(transactionId: Long, userId: Long) {
        val transaction = getTransactionById(transactionId)
        val user = userService.getUserById(userId)

        transaction.changeAction(Action.CANCEL)
        transaction.changeState(OperationState.INACTIVE)

        user.modifyReputation({ a, b -> a - b}, 20)

        transactionRepository.save(transaction)
    }


    private fun isPast30Minutes(initTime: String): Boolean {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        val transactionTime: LocalDateTime = LocalDateTime.parse(initTime, formatter)
        val currentTime = LocalDateTime.now()

        val differenceInMinutes = ChronoUnit.MINUTES.between(transactionTime, currentTime)

        return differenceInMinutes > 30
    }

}
