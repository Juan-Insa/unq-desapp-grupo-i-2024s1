package ar.edu.unq.desapp.grupoI.backenddesappapi.webservice.controllers

data class TransactionRequest(
    val intentionId: Long,
    val userId: Long
)