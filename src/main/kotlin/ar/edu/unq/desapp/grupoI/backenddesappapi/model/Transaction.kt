package ar.edu.unq.desapp.grupoI.backenddesappapi.model

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Action

class Transaction (
    val cryptoAsset: CryptoCurrency,
    val amount: Double,
    val cryptoCurrencyPrice: Double,
    val operationAmount: Double,
    val userName: String,
    val numberOfOperations: Int,
    val reputation: Double,
    val destinationAddress: String,
    val action: Action
)