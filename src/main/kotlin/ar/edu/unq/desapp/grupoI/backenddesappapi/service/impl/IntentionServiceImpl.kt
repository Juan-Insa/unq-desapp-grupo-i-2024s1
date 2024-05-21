package ar.edu.unq.desapp.grupoI.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoI.backenddesappapi.exceptions.IntentionNotFoundException
import ar.edu.unq.desapp.grupoI.backenddesappapi.exceptions.UserNotFoundException
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.Dolar
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.Intention
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.OperationState
import ar.edu.unq.desapp.grupoI.backenddesappapi.persistence.repository.IntentionRepository
import ar.edu.unq.desapp.grupoI.backenddesappapi.service.CryptoCurrencyService
import ar.edu.unq.desapp.grupoI.backenddesappapi.service.IntentionService
import ar.edu.unq.desapp.grupoI.backenddesappapi.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestTemplate
import kotlin.jvm.optionals.getOrNull

@Service
@Transactional
class IntentionServiceImpl: IntentionService {

    @Autowired lateinit var intentionRepository: IntentionRepository
    @Autowired lateinit var cryptoCurrencyService: CryptoCurrencyService
    @Autowired lateinit var userService: UserService
    val restTemplate: RestTemplate = RestTemplate()

    override fun createIntention(intention: Intention, userId: Long): Intention {
        cryptoCurrencyService.validatePrice(intention.cryptoAsset.toString(), intention.price)

        val actualDolar = getCurrentDolarPrice()
        val priceInPesos = getPriceInPesos(intention.price, actualDolar)
        intention.priceInPesos = priceInPesos

        val user = userService.getUserById(userId)
        intention.user = user

        intentionRepository.save(intention)

        return intention
    }
    private fun getCurrentDolarPrice(): Dolar {
        val apiUrl = "https://dolarapi.com/v1/dolares/blue"
        return restTemplate.getForObject(apiUrl, Dolar::class.java)
            ?: throw RuntimeException("No se pudo obtener el precio del dólar")
    }
    private fun getPriceInPesos(price: Double, dolar: Dolar): Double {
        val dolarAvgPrice = (dolar.compra + dolar.venta) / 2.0
        return price * dolarAvgPrice
    }
    override fun getIntentionById(id: Long): Intention {
        return intentionRepository.findById(id)
            .getOrNull() ?: throw IntentionNotFoundException("Could not find an intention with id: `${id}`")
    }

    override fun getAllIntentions(): MutableList<Intention> {
        return intentionRepository.findAll() as MutableList<Intention>
    }

    override fun getActiveIntentions(): List<Intention> {
        return intentionRepository.findByState(OperationState.ACTIVE)
    }

    override fun getActiveIntentionsFrom(userId: Long): List<Intention> {
        try {
            userService.getUserById(userId)
        } catch (e: UserNotFoundException) {
            throw UserNotFoundException("User with $userId not found")
        }
        return intentionRepository.findByUserIdAndState(userId, OperationState.ACTIVE)
    }

    override fun saveIntention(intention: Intention): Intention {
        return intentionRepository.save(intention)
    }
}