package ar.edu.unq.desapp.grupoI.backenddesappapi.webservice.controllers

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.Intention

data class IntentionRequest(
    val intention: Intention,
    val userId: Long,
)