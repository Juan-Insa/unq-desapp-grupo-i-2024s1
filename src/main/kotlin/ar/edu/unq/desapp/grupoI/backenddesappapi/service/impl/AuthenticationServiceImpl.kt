package ar.edu.unq.desapp.grupoI.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoI.backenddesappapi.persistence.repository.UserRepository
import ar.edu.unq.desapp.grupoI.backenddesappapi.service.AuthenticationService
import ar.edu.unq.desapp.grupoI.backenddesappapi.utils.UserRegisterValidator
import ar.edu.unq.desapp.grupoI.backenddesappapi.webservice.controllers.dto.LoginUserDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AuthenticationServiceImpl(): AuthenticationService {

    @Autowired lateinit var userRepository: UserRepository
    @Autowired lateinit var passwordEncoder: PasswordEncoder
    @Autowired lateinit var authenticationManager: AuthenticationManager

    override fun signup(user: User): User {

        require(!(userRepository.existsByEmail(user.email))) { "Email is already registered" }

        UserRegisterValidator.validateUserData(user)

        val userToRegister = User(
            name = user.name ,
            lastName = user.lastName ,
            email = user.email,
            address = user.address,
            cvu = user.cvu,
            cryptoWalletAddress = user.cryptoWalletAddress,
            password = passwordEncoder.encode(user.password)
        )
        return userRepository.save(userToRegister)
    }

    override fun authenticate(loginUserDTO: LoginUserDTO): User {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginUserDTO.email,
                loginUserDTO.password
            )
        )
        return userRepository.findByEmail(loginUserDTO.email!!).orElseThrow()
    }

}