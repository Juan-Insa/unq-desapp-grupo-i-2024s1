package ar.edu.unq.desapp.grupoI.backenddesappapi.webservice.controllers.dto

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.User

class UserDTO(
    val id:Long?,
    var name: String,
    var lastName: String,
    var email: String,
    var adress: String,
    var password: String,
    var cbu: String,
    var criptoWalletAdress: String,
) {
    fun aModelo(): User {
        val user = User(name, lastName, email, adress, password, cbu, criptoWalletAdress)
        user.id = id

        return user
    }

}