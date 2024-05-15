package ar.edu.unq.desapp.grupoI.backenddesappapi.service

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.Intention
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Asset
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Operation

interface IntentionService {
    fun createIntention(userEmail: String, cryptoAsset: Asset, amount: Double, operation: Operation, price: Double): Intention
    fun getIntentionById(id: Long): Intention
    fun getAllIntentions(): MutableList<Intention>
}
