package ar.edu.unq.desapp.grupoI.backenddesappapi.helpers

import ar.edu.unq.desapp.grupoI.backenddesappapi.exceptions.*
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.User

class UserRegisterValidator {

    companion object {
        fun validateUserData(user: User) {
            validateName(user.name)
            validateLastName(user.lastName)
            validateEmail(user.email)
            validateAdress(user.address)
            validatePassword(user.password)
            validateCvu(user.cvu)
            validateCriptoWalletAddress(user.cryptoWalletAddress)
        }

        fun validateName(name: String) {
            if (name.length !in 3..30){
                throw InvalidNameException()
            }
        }

        fun validateLastName(lastName: String) {
            if (lastName.length !in 3..30) {
                throw InvalidLastNameException()
            }
        }

        fun validateEmail(email: String) {
            if (!email.matches(Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"))
                || email.startsWith(".")
                || email.endsWith(".")) {
                throw InvalidEmailException()
            }
        }

        fun validateAdress(adress: String) {
            if(adress.length !in 10..30) {
                throw InvalidAdressException()
            }
        }

        fun validatePassword(password: String) {
            if(password.length < 6) { throw InvalidPasswordException("The password must be at least 6 characters in length" ) }
            if(password.none { it.isUpperCase() }) { throw InvalidPasswordException("The password must contain at least one upper case letter") }
            if(password.none { it.isLowerCase() }) { throw InvalidPasswordException("The password must contain at least one lower case letter") }
            if(password.none { !it.isLetterOrDigit() }) { throw InvalidPasswordException("The password must contain at least one special characte") }
        }

        fun validateCvu(cbu: String) {
            if(cbu.length != 22 || cbu.any { !it.isDigit() }) {
                throw InvalidCVUException()
            }
        }

        fun validateCriptoWalletAddress(criptoWalletAdress: String) {
            if(criptoWalletAdress.length != 8 || criptoWalletAdress.any { !it.isDigit() }) {
                throw InvalidCryptoWalletAddressException()
            }
        }
    }
}
