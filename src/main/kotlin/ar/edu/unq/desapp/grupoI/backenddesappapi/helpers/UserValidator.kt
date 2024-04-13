package ar.edu.unq.desapp.grupoI.backenddesappapi.helpers

import ar.edu.unq.desapp.grupoI.backenddesappapi.controllers.dto.UserDTO

class UserValidator {

    companion object {
        fun validateUser(userDTO: UserDTO) {
            validateName(userDTO.name)
            validateLastName(userDTO.lastName)
            validateEmail(userDTO.email)
            validateAddress(userDTO.adress)
            validatePassword(userDTO.password)
            validateCbu(userDTO.cbu)
            validateCriptoWalletAddress(userDTO.criptoWalletAdress)
        }

        private fun validateName(name: String) {
            require(name.length in 3..30) {
                "The user name must be between 3 to 30 characters in length"
            }
        }

        private fun validateLastName(lastName: String) {
            require(lastName.length in 3..30) {
                "The user last name must be between 3 to 30 characters in length"
            }
        }

        private fun validateEmail(email: String) {
            require(email.matches(Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"))) {
                "Invalid email format"
            }
        }

        private fun validateAddress(adress: String) {
            require(adress.length in 10..30) {
                "The adress must be between 10 to 30 characters in length"
            }
        }

        private fun validatePassword(password: String) {
            require(password.length >= 6) { "The password must be at least 6 characters in length" }
            require(password.any { it.isUpperCase() }) { "The password must contain at least one upper case letter" }
            require(password.any { it.isLowerCase() }) { "The password must contain at least one lower case letter" }
            require(password.any { !it.isLetterOrDigit() }) { "The password must contain at least one special characte" }


        }

        private fun validateCbu(cbu: String) {
            require(cbu.length == 22 && cbu.all { it.isDigit() }) {
                "The CVU must be 22 digits"
            }
        }

        private fun validateCriptoWalletAddress(criptoWalletAdress: String) {
            require(criptoWalletAdress.length == 8 && criptoWalletAdress.all { it.isDigit() }) {
                "The wallet adress must be 8 digits"
            }
        }
    }
}
