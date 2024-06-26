package ar.edu.unq.desapp.grupoI.backenddesappapi.webservice.controllers.dto

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.User

class UserDTO(
    val id:Long?,
    val name: String,
    val lastName: String,
    val email: String,
    val address: String,
    val cvu: String,
    val criptoWalletAddress: String,
) {
    companion object {
        fun fromModel(user: User): UserDTO {
            return UserDTO(
                id = user.id,
                name = user.name,
                lastName = user.lastName,
                email = user.email,
                address = user.address,
                cvu = user.cvu,
                criptoWalletAddress = user.cryptoWalletAddress
            )
        }
    }

}