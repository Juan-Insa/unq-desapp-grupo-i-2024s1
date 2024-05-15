package ar.edu.unq.desapp.grupoI.backenddesappapi.webservice.controllers

data class IntentionRequest(
    val userEmail: String,
    val cryptoAsset: String,
    val amount: Double,
    val operation: String,
    val price: Double
)