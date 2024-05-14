package ar.edu.unq.desapp.grupoI.backenddesappapi.service

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.ActiveIntentions
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.Intention
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Asset
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Operation

interface IntentionService {
    fun createIntention(user: User, cryptoAsset: Asset, amount: Double, operation: Operation, price: Double): Intention
    fun getIntentionById(id: Long): Intention
    fun getAllIntentions(): MutableList<Intention>
}
