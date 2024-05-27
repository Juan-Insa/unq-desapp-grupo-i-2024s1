package ar.edu.unq.desapp.grupoI.backenddesappapi.service

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.OperatedVolume
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.Transaction
import java.time.LocalDateTime

interface TransactionService {
    fun createTransaction(intentionId: Long, interestedUserId: Long): Transaction
    fun getTransactionById(id: Long): Transaction
    fun finishTransaction(transactionId: Long): Transaction
    fun cancelTransaction(transactionId: Long, userId:Long): Transaction
    fun isPast30Minutes(initTime: LocalDateTime): Boolean
    fun getOperatedVolumeFor(userId: Long, startDate: String, endDate: String): OperatedVolume

}