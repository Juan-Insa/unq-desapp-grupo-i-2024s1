package ar.edu.unq.desapp.grupoI.backenddesappapi.service

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.Intention

interface IntentionService {
    fun createIntention(intention: Intention, userId: Long): Intention
    fun getIntentionById(id: Long): Intention
    fun getAllIntentions(): MutableList<Intention>
    fun saveIntention(intention: Intention): Intention
    fun getActiveIntentions(): List<Intention>
    fun getActiveIntentionsFrom(userId: Long): List<Intention>
}
