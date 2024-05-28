package ar.edu.unq.desapp.grupoI.backenddesappapi.webservice.controllers.dto

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.Intention
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.Transaction
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Action
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.OperationState
import java.time.LocalDateTime

class TransactionDTO(
    val interestedUserEmail: String,
    val action: Action,
    val initTime: LocalDateTime,
    val state: OperationState,
    val intention: Intention
) {

    companion object {
        fun fromModel(transaction: Transaction): TransactionDTO {
            return TransactionDTO(
                interestedUserEmail = transaction.interestedUser.email,
                action = transaction.action,
                initTime = transaction.initTime,
                state = transaction.state,
                intention = transaction.intention
            )
        }
    }
}