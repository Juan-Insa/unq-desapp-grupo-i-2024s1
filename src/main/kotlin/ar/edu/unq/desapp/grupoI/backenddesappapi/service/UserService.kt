package ar.edu.unq.desapp.grupoI.backenddesappapi.service

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.User

interface UserService {
    fun getUserByName(name: String): User
    fun registerUser(user: User): User

}