package ar.edu.unq.desapp.grupoI.backenddesappapi.controllers

import ar.edu.unq.desapp.grupoI.backenddesappapi.controllers.dto.UserDTO
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoI.backenddesappapi.service.UserService
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
class UserControllerREST {

    @Autowired lateinit var  userService: UserService

    @PostMapping("/register")
    fun registerUser(@RequestBody user: UserDTO): ResponseEntity<User> {
        val registeredUser = userService.registerUser(user.aModelo())

        return ResponseEntity(registeredUser, HttpStatus.CREATED)
    }

}