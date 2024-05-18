package ar.edu.unq.desapp.grupoI.backenddesappapi.service

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.User

interface UserService {

    fun getUserByEmail(name: String): User
    fun getUserById(id: Long): User
    fun registerUser(user:User): User
    fun saveUser(user: User): User
}