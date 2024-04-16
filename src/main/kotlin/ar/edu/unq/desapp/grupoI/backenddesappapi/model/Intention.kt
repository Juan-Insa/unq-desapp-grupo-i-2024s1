package ar.edu.unq.desapp.grupoI.backenddesappapi.model

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Action
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Asset
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Operation

class Intention(
    val user: User,
    val cryptoAsset: Asset,
    val amount: Double,
    val operation: Operation,
    val priceInPesos: Double = 0.0,
    val price: Double = 0.0,
) {

    fun generateTransaction(interestedUser: User) {
        val destinationAddress : String

        if (this.operation == Operation.SELL) {
            destinationAddress = interestedUser.cvu
        } else {
            destinationAddress = interestedUser.criptoWalletAdress
        }

        val transaction = Transaction(
            cryptoAsset= cryptoAsset,
            nominalAmount = amount,
            cryptoCurrencyPrice = price,
            userEmail = interestedUser.email,
            numberOfOperations = interestedUser.operations,
            reputation = interestedUser.reputation,
            destinationAddress = destinationAddress,
            action = Action.AWAITING
            )

        user.addTransaction(transaction)
    }

}