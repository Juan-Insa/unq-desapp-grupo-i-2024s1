package ar.edu.unq.desapp.grupoI.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoI.backenddesappapi.exceptions.UserNotFoundException
import ar.edu.unq.desapp.grupoI.backenddesappapi.helpers.UserRegisterValidator
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoI.backenddesappapi.persistence.repository.UserRepository
import ar.edu.unq.desapp.grupoI.backenddesappapi.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

@Service
@Transactional
class UserServiceImpl(): UserService {

    @Autowired lateinit var userRepository: UserRepository

    override fun getUserByEmail(email: String): User {
        return userRepository.findByEmail(email)
            .getOrNull() ?: throw UserNotFoundException("could not find user with name `${email}`")
    }

    override fun registerUser(name: String, lastName: String, email: String, address: String, password: String,
        cvu: String, cwAddress: String): User {

        if (userRepository.existsByEmail(email)) {
            throw IllegalArgumentException("Email is already registered");
        }

        val user = User(name,lastName,email,address,password,cvu,cwAddress)

        return userRepository.save(user)
    }

}