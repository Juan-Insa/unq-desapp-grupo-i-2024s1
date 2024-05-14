package ar.edu.unq.desapp.grupoI.backenddesappapi.webservice.controllers

data class UserRequest(
    val name: String,
    val lastName: String,
    val email: String,
    val address: String,
    val password: String,
    val cvu: String,
    val criptoWalletAddress: String
)