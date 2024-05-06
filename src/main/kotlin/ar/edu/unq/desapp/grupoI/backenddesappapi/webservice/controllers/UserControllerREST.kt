package ar.edu.unq.desapp.grupoI.backenddesappapi.webservice.controllers

import ar.edu.unq.desapp.grupoI.backenddesappapi.webservice.controllers.dto.UserDTO
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoI.backenddesappapi.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@Transactional
@RequestMapping("api/user")
class UserControllerREST {

    @Autowired lateinit var  userService: UserService

    @PostMapping("/register")
    fun registerUser(
        @RequestParam name: String,
        @RequestParam lastName: String,
        @RequestParam email: String,
        @RequestParam address: String,
        @RequestParam password: String,
        @RequestParam cvu: String,
        @RequestParam criptoWalletAddress: String
    ): ResponseEntity<User> {

        val registeredUser = userService.registerUser(
            name,
            lastName,
            email,
            address,
            password,
            cvu,
            criptoWalletAddress
        )

        return ResponseEntity(registeredUser, HttpStatus.CREATED)
    }

}