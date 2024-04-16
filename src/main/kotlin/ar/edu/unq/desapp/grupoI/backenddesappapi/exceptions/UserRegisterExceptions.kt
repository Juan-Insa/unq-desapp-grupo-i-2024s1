package ar.edu.unq.desapp.grupoI.backenddesappapi.exceptions

import java.lang.IllegalArgumentException

class InvalidNameException(msg: String = "The user name must be between 3 to 30 characters in length"): IllegalArgumentException(msg) {  }
class InvalidLastNameException(msg: String = "The user last name must be between 3 to 30 characters in length"): IllegalArgumentException(msg) {  }
class InvalidEmailException(msg: String = "Invalid email format"): IllegalArgumentException(msg) {  }
class InvalidAdressException(msg: String = "The adress must be between 10 to 30 characters in length"): IllegalArgumentException(msg) {  }
class InvalidPasswordException(msg: String): IllegalArgumentException(msg) {  }
class InvalidCVUException(msg: String = "The CVU must be 22 digits in length"): IllegalArgumentException(msg) {  }
class InvalidCryptoWalletAddressException(msg: String = "The wallet adress must be 8 digits in length"): IllegalArgumentException(msg) {  }