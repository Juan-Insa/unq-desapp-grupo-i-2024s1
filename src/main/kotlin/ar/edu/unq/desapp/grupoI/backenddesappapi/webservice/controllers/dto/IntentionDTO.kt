package ar.edu.unq.desapp.grupoI.backenddesappapi.webservice.controllers.dto

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.Intention
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Asset
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Operation

class IntentionDTO(
    val cryptoAsset: Asset,
    val amount: Double,
    val operation: Operation,
    val price: Double,
    val userEmail: String
) {

    companion object {
        fun fromModel(intention: Intention): IntentionDTO {
            return IntentionDTO(
                cryptoAsset = intention.cryptoAsset,
                amount = intention.amount,
                operation = intention.operation,
                price = intention.price,
                userEmail = intention.user!!.email
            )
        }
    }
}