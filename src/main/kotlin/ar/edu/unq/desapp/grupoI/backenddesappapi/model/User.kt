package ar.edu.unq.desapp.grupoI.backenddesappapi.model

import ar.edu.unq.desapp.grupoI.backenddesappapi.helpers.UserRegisterValidator
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Asset
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Operation
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "users")
class User(
    var name: String,
    var lastName: String,
    var email: String,
    var address: String,
    var password: String,
    var cvu: String,
    var cryptoWalletAddress: String,
    var reputation: Int = 0,

    ) {
    var operations: Int = 0

    @OneToMany(mappedBy = "interestedUser", cascade = [CascadeType.ALL], fetch = FetchType.LAZY, orphanRemoval = true)
    var transactions: MutableList<Transaction> = mutableListOf()

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun modifyReputation(op: (Int, Int) -> Int, num: Int) {
        reputation = op(reputation, num)
    }

    fun addTransaction(transaction: Transaction) {
        transactions.add(transaction)
    }

}


