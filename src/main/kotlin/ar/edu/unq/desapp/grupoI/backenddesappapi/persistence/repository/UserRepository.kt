package ar.edu.unq.desapp.grupoI.backenddesappapi.persistence.repository

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserRepository: CrudRepository<User, Long> {

    fun findByEmail(email: String): Optional<User>
    fun existsByEmail(email: String): Boolean
}