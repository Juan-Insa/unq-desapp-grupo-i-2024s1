package ar.edu.unq.desapp.grupoI.backenddesappapi.model

import ar.edu.unq.desapp.grupoI.backenddesappapi.exceptions.*
import ar.edu.unq.desapp.grupoI.backenddesappapi.helpers.UserRegisterValidator
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRegisterValidatorTest {

    @Test
    fun `when name is less than 3 characters it throws an InvalidNameException`() {
        val name = "a"

        assertThrows<InvalidNameException> { UserRegisterValidator.validateName(name) }
    }

    @Test
    fun `when name is more than 30 characters it throws an InvalidNameException`() {
        val name = "safasffanskgankgmapgmlapgoamgopamgo"

        assertThrows<InvalidNameException> { UserRegisterValidator.validateName(name) }
    }

    @Test
    fun `when last name is less than 3 characters it throws an InvalidLastNameException`() {
        val lastName = "l"

        assertThrows<InvalidLastNameException> { UserRegisterValidator.validateLastName(lastName) }
    }

    @Test
    fun `when the last name is more than 30 characters it throws an InvalidLastNameException`() {
        val lastName = "asfkdgmakniaiasgnjiagnpangamopga"

        assertThrows<InvalidLastNameException> { UserRegisterValidator.validateLastName(lastName) }
    }

    @Test
    fun `when the email format is valid it does not throw any exception`() {
        val validEmails = listOf(
            "user@example.com",
            "user123@example.com",
            "user.name@example.com",
            "user123.name@example.com",
            "user_name@example.com",
            "user+name@example.com",
            "user-name@example.com",
            "user123@example.co.uk",
            "user@subdomain.example.com",
        )

        validEmails.forEach {
            assertDoesNotThrow { UserRegisterValidator.validateEmail(it) }
        }
    }

    @Test
    fun `when the email format is invalid it throws an InvalidEmailException`() {
        val invalidEmails = listOf(
            "user@example_.com",
            "user",
            "user@",
            "@example.com",
            "user.example.com",
            "user@.com",
            "user@example",
            "user@.123",
            "user@_example.com",
            "user@[192.168.1.256]",
            "user@[IPv6:2001:db8::1:]",
            "user@[IPv6:2001:db8::1]a",
            "user@example.",
            "user@.example",
            ".user@example.com",
        )

        invalidEmails.forEach {
            assertThrows<InvalidEmailException> { UserRegisterValidator.validateEmail(it) }
        }
    }

    @Test
    fun `when the address is less than 10 characters it throws an InvalidAdressException`() {
        val address = "fails"
        assertThrows<InvalidAdressException> { UserRegisterValidator.validateAdress(address) }
    }

    @Test
    fun `when the address is more than 30 characters it throws an InvalidAdressException`() {
        val address = "agnklñasjngñkaglkangslanalnhaln"
        assertThrows<InvalidAdressException> { UserRegisterValidator.validateAdress(address) }
    }

    @Test
    fun `when the password does not contain at least one lower case character it throws an InvalidPasswordException`() {
        val password = "ALLUPPERCASE.PASSWORD"

        assertThrows<InvalidPasswordException> { UserRegisterValidator.validatePassword(password) }
    }

    @Test
    fun `when the password does not contain at least one upper case character it throws an InvalidPasswordException`() {
        val password = "all.lowercase.password"

        assertThrows<InvalidPasswordException> { UserRegisterValidator.validatePassword(password) }
    }

    @Test
    fun `when the password does not contain at least one special character it throws an InvalidPasswordException`() {
        val password = "NOSPECIALcharacterpassword"

        assertThrows<InvalidPasswordException> { UserRegisterValidator.validatePassword(password) }
    }

    @Test
    fun `when the password is valid it throws no exception`() {
        val password = "Valid.Password"

        assertDoesNotThrow { UserRegisterValidator.validatePassword(password) }
    }

    @Test
    fun `when the CVU does not contain exactly 22 digits it throws an InvalidCVUException` () {
        val cvu = "123"

        assertThrows<InvalidCVUException> { UserRegisterValidator.validateCvu(cvu) }
    }

    @Test
    fun `when the CVU does not contain all digits it throws an InvalidCVUException` () {
        val cvu = "1234LNMA90123456789012"

        assertThrows<InvalidCVUException> { UserRegisterValidator.validateCvu(cvu) }
    }

    @Test
    fun `when the CVU contains 22 digits it throws no exception` () {
        val cvu = "1234567890123456789012"

        assertDoesNotThrow { UserRegisterValidator.validateCvu(cvu) }
    }

    @Test
    fun `when the crypto wallet address is not 8 digits long it throws an InvalidCryptoWalletAdressException` () {
        val cryptoWalletAddress = "123"

        assertThrows<InvalidCryptoWalletAddressException> {
            UserRegisterValidator.validateCriptoWalletAddress(cryptoWalletAddress)
        }
    }

    @Test
    fun `when the crypto wallet address does not contain all digits it throws an InvalidCryptoWalletAdressException` () {
        val cryptoWalletAddress = "1234ABC8"

        assertThrows<InvalidCryptoWalletAddressException> {
            UserRegisterValidator.validateCriptoWalletAddress(cryptoWalletAddress)
        }
    }

    @Test
    fun `when the crypto wallet address contains 8 digits it throws no exception` () {
        val cryptoWalletAddress = "12345678"

        assertDoesNotThrow { UserRegisterValidator.validateCriptoWalletAddress(cryptoWalletAddress) }
    }
}