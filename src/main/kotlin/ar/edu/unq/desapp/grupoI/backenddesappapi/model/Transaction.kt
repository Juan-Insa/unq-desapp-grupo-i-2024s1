package ar.edu.unq.desapp.grupoI.backenddesappapi.model

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Action
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Asset

class Transaction (
    val cryptoAsset: Asset,
    val nominalAmount: Double,
    val cryptoCurrencyPrice: Double,
    //val operationAmount: Double,
    val userEmail: String,
    val numberOfOperations: Int,
    val reputation: Int,
    val destinationAddress: String,
    val action: Action
)