package ar.edu.unq.desapp.grupoI.backenddesappapi.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
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

    ) {
    var reputation: Int = 0
    var operations: Int = 0

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun modifyReputation(op: (Int, Int) -> Int, num: Int) {
        val newReputation = op(reputation, num)
        reputation = when {
            newReputation < 0 -> 0
            newReputation > 100 -> 100
            else -> newReputation
        }
    }


}


