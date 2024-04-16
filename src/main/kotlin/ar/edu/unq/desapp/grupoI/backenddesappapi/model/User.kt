package ar.edu.unq.desapp.grupoI.backenddesappapi.model

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Asset
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Operation
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "user")
class User(
    var name: String,
    var lastName: String,
    var email: String,
    var address: String,
    var password: String,
    var cvu: String,
    var criptoWalletAdress: String,
    var reputation: Int = 0,

    ) {
    var operations: Int = 0

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun modifyReputation(op: (Int, Int) -> Int, num: Int) {
        reputation = op(reputation, num)
    }

    fun postIntent(symbol: Asset, amount: Float, price: Float, localPrice: Float, operation: Operation): Intention {
        val intention = Intention(
            userEmail = this.email,
            cryptoAsset = symbol,
            amount = amount,
            price = price,
            priceInPesos = localPrice,
            operation = operation
        )

        ActiveIntentions.addIntention(intention)

        return intention
    }

}


