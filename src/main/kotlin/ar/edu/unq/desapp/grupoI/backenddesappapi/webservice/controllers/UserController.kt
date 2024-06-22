package ar.edu.unq.desapp.grupoI.backenddesappapi.webservice.controllers

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoI.backenddesappapi.security.JwtService
import ar.edu.unq.desapp.grupoI.backenddesappapi.service.UserService
import ar.edu.unq.desapp.grupoI.backenddesappapi.webservice.controllers.dto.LoginResponse
import ar.edu.unq.desapp.grupoI.backenddesappapi.webservice.controllers.dto.LoginUserDTO
import ar.edu.unq.desapp.grupoI.backenddesappapi.webservice.controllers.request.UserRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Transactional
@RequestMapping("api/user")
@Tag(name = "User")
class UserController {

    @Autowired lateinit var userService: UserService
    @Autowired lateinit var jwtService: JwtService

    @Operation(
        summary = "Register a new user",
        description = "Register a new user. Pass has to be longer to 6 character and have one upper case and one special character. Cvu has to have 22 lenght long and only digits. Cryptowallet address has to have 8 lenght long and only digits. ")
    @PostMapping("/register")
    fun registerUser(@RequestBody userRequest: UserRequest): ResponseEntity<User> {
        val registeredUser = userService.registerUser(userRequest.user)

        return ResponseEntity(registeredUser, HttpStatus.CREATED)
    }

    @PostMapping("/login")
    fun authenticate(@RequestBody loginUserDTO: LoginUserDTO): ResponseEntity<LoginResponse> {
        val authenticatedUser: User = userService.authenticate(loginUserDTO)

        val jwtToken: String = jwtService.generateToken(authenticatedUser)

        val loginResponse = LoginResponse(jwtToken, jwtService.getExpirationTime())

        return ResponseEntity.ok(loginResponse)
    }

}

