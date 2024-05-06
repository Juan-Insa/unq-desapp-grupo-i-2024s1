package ar.edu.unq.desapp.grupoI.backenddesappapi.service

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.Intention
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.Transaction
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.User

interface TransactionService {
    fun createTransaction(intention: Intention, interestedUser: User): Transaction
    fun getTransactionById(id: Long): Transaction

}