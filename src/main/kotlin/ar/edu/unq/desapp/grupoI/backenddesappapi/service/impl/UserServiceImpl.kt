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

    override fun getUserByName(name: String): User {
        return userRepository.findByName(name)
            .getOrNull() ?: throw UserNotFoundException("could not found user with name `${name}`")
    }

    override fun registerUser(user: User): User {
        if (userRepository.existsByEmail(user.email)) {
            throw IllegalArgumentException("Email is already registered");
        }
        UserRegisterValidator.validateUser(user)
        return userRepository.save(user)
    }

}