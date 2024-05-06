package ar.edu.unq.desapp.grupoI.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoI.backenddesappapi.exceptions.TransactionNotFoundException
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.Intention
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.Transaction
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Action
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Operation
import ar.edu.unq.desapp.grupoI.backenddesappapi.persistence.repository.TransactionRepository
import ar.edu.unq.desapp.grupoI.backenddesappapi.service.TransactionService
import ar.edu.unq.desapp.grupoI.backenddesappapi.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

@Service
@Transactional
class TransactionServiceImpl: TransactionService {

    @Autowired lateinit var userService: UserService
    @Autowired lateinit var transactionRepository: TransactionRepository

    override fun createTransaction(intention: Intention, interestedUser: User): Transaction {
        // crear la transacción
        val intentionUser = userService.getUserByEmail(intention.userEmail)

        val destinationAddress : String

        if (intention.operation == Operation.SELL) {
            destinationAddress = intentionUser.cvu
        }
        else {
            destinationAddress = intentionUser.criptoWalletAddress
        }

        val transaction = Transaction(
            cryptoAsset= intention.cryptoAsset,
            nominalAmount = intention.amount,
            cryptoCurrencyPrice = intention.price,
            userEmail = interestedUser.email,
            numberOfOperations = interestedUser.operations,
            reputation = interestedUser.reputation,
            destinationAddress = destinationAddress,
            action = Action.AWAITING
        )

        // notificar al user ( TO BE DONE )

        // persistir transacción
        transactionRepository.save(transaction)
        return transaction
    }

    override fun getTransactionById(id: Long): Transaction {
        return transactionRepository.findById(id)
            .getOrNull() ?: throw TransactionNotFoundException("Could not find a transaction with id: `${id}`")
    }


}
