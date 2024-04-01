package ar.edu.unq.desapp.grupoI.backenddesappapi.model

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Operation

class Intention(
    val user: User,
    val cryptoAsset: CryptoAsset,
    val amount: Double,
    val operation: Operation,
) {
    val priceInPesos: Double = 0.0
    val price: Double = 0.0
}