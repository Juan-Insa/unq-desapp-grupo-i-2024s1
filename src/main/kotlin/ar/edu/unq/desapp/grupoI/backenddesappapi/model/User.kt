package ar.edu.unq.desapp.grupoI.backenddesappapi.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "users")
class User(
    var name: String,
    var lastName: String,
    var email: String,
    var address: String,
    var cvu: String,
    var cryptoWalletAddress: String,
    private var password: String
) : UserDetails {

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

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf();
    }

    override fun getUsername(): String {
        return email
    }

    override fun getPassword(): String {
        return password
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}