package ar.edu.unq.desapp.grupoI.backenddesappapi.service

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoI.backenddesappapi.webservice.controllers.dto.LoginUserDTO

interface AuthenticationService {
    fun signup(user: User): User
    fun authenticate(loginUserDTO: LoginUserDTO): User
}