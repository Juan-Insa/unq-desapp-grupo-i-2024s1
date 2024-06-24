package ar.edu.unq.desapp.grupoI.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoI.backenddesappapi.exceptions.UserNotFoundException
import ar.edu.unq.desapp.grupoI.backenddesappapi.utils.UserRegisterValidator
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoI.backenddesappapi.persistence.repository.UserRepository
import ar.edu.unq.desapp.grupoI.backenddesappapi.service.UserService
import ar.edu.unq.desapp.grupoI.backenddesappapi.webservice.controllers.dto.LoginUserDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

@Service
@Transactional
class UserServiceImpl(): UserService {

    @Autowired lateinit var userRepository: UserRepository
    @Autowired lateinit var passwordEncoder: PasswordEncoder
    @Autowired lateinit var authenticationManager: AuthenticationManager

    override fun getUserByEmail(email: String): User {
        return userRepository.findByEmail(email)
            .getOrNull() ?: throw UserNotFoundException("could not find user with email `${email}`")
    }

    override fun getUserById(id: Long): User {
        return userRepository.findById(id)
            .getOrNull() ?: throw UserNotFoundException("could not find user with id `${id}`")
    }

    override fun saveUser(user: User): User {
        return userRepository.save(user)
    }

}