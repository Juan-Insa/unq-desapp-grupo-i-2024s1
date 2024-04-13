package ar.edu.unq.desapp.grupoI.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoI.backenddesappapi.exceptions.UserNotFoundException
import ar.edu.unq.desapp.grupoI.backenddesappapi.persistence.repository.UserRepository
import ar.edu.unq.desapp.grupoI.backenddesappapi.service.UserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

@Service
@Transactional
class UserDetailsServiceImpl : UserDetailsService {

    @Autowired lateinit var userRepository: UserRepository

    override fun loadUserByName(name: String): UserDetails {
        val user = userRepository.findByName(name).getOrNull() ?: throw UserNotFoundException("User with name $name not found")
        return org.springframework.security.core.userdetails.User(user.name, user.password, emptyList())
    }

}