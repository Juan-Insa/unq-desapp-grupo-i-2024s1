package ar.edu.unq.desapp.grupoI.backenddesappapi.webservice.controllers

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.Intention

data class TransactionRequest(
    val intentionId: Long,
    val userId: Long
)