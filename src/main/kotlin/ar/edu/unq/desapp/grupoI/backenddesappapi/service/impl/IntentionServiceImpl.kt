package ar.edu.unq.desapp.grupoI.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoI.backenddesappapi.exceptions.IntentionNotFoundException
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.Intention
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Asset
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Operation
import ar.edu.unq.desapp.grupoI.backenddesappapi.persistence.repository.IntentionRepository
import ar.edu.unq.desapp.grupoI.backenddesappapi.service.IntentionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

@Service
@Transactional
class IntentionServiceImpl: IntentionService {

    @Autowired lateinit var intentionRepository: IntentionRepository

    override fun createIntention(user: User, cryptoAsset: Asset, amount: Double, operation: Operation, price: Double): Intention {
        // acá falta la lógica que calcula el precio en pesos del crypto-activo por ahora queda así
        val priceInPesos = price * 1000

        val intention = Intention(
            userName  = user.name + " " + user.lastName,
            userEmail = user.email,
            cryptoAsset = cryptoAsset,
            amount = amount,
            operation = operation,
            price = price,
            priceInPesos = priceInPesos
        )

        intentionRepository.save(intention)

        return intention
    }

    override fun getIntentionById(id: Long): Intention {
        return intentionRepository.findById(id)
            .getOrNull() ?: throw IntentionNotFoundException("Could not find an intention with id: `${id}`")
    }
}