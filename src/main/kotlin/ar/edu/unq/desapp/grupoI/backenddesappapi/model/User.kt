package ar.edu.unq.desapp.grupoI.backenddesappapi.model

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
    var adress: String,
    var password: String,
    var cbu: String,
    var criptoWalletAdress: String,
    var reputation: Int = 0
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun modifyReputation(op: (Int, Int) -> Int, num: Int) {
       reputation = op(reputation, num)
    }
}