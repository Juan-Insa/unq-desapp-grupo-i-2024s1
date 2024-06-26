package ar.edu.unq.desapp.grupoI.backenddesappapi.service

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoI.backenddesappapi.webservice.controllers.dto.LoginUserDTO

interface UserService {

    fun getUserByEmail(email: String): User
    fun getUserById(id: Long): User
    fun saveUser(user: User): User

}