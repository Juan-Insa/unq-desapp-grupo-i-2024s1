package ar.edu.unq.desapp.grupoI.backenddesappapi.webservice.controllers.request

data class TransactionRequest(
    val intentionId: Long,
    val userId: Long
)