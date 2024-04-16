package ar.edu.unq.desapp.grupoI.backenddesappapi.model

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Asset
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Operation

class Intention(
    val userEmail: String,
    val cryptoAsset: Asset,
    val amount: Float,
    val operation: Operation,
    val priceInPesos: Float = 0.0f,
    val price: Float = 0.0f,
) {

}