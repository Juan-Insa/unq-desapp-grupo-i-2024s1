package ar.edu.unq.desapp.grupoI.backenddesappapi.webservice.controllers

data class IntentionRequest(
    val userName: String,
    val userEmail: String,
    val cryptoAsset: String,
    val amount: Double,
    val operation: String,
    val price: Double
)