package ar.edu.unq.desapp.grupoI.backenddesappapi.model

import ar.edu.unq.desapp.grupoI.backenddesappapi.helpers.UserRegisterValidator
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
    var criptoWalletAddress: String,
    var reputation: Int = 0,

    ) {
    var operations: Int = 0
    //var currentTransactions: MutableList<Transaction> = mutableListOf()

    init {
        UserRegisterValidator.validateUserData(name, lastName, email, address, password, cvu, criptoWalletAddress)
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun modifyReputation(op: (Int, Int) -> Int, num: Int) {
        reputation = op(reputation, num)
    }

    fun postIntent(symbol: Asset, amount: Double, price: Double, localPrice: Double, operation: Operation): Intention {
        val intention = Intention(
            userName = this.name + " " + this.lastName,
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

    //fun addTransaction(transaction: Transaction) {
    //    currentTransactions.add(transaction)
    //}




}


