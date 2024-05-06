package ar.edu.unq.desapp.grupoI.backenddesappapi.service

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.User

interface UserService {
    fun getUserByEmail(name: String): User
    fun registerUser(name: String, lastName: String, email: String, address: String, password: String, cvu: String,
                     cryptoWalletAddress: String): User


}