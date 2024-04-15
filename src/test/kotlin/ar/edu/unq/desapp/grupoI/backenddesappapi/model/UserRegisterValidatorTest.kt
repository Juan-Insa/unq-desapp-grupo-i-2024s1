package ar.edu.unq.desapp.grupoI.backenddesappapi.model

import ar.edu.unq.desapp.grupoI.backenddesappapi.exceptions.InvalidAdressException
import ar.edu.unq.desapp.grupoI.backenddesappapi.exceptions.InvalidEmailException
import ar.edu.unq.desapp.grupoI.backenddesappapi.exceptions.InvalidLastNameException
import ar.edu.unq.desapp.grupoI.backenddesappapi.exceptions.InvalidNameException
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
    fun `when the email is valid it does not throw any exception`() {
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
    fun `when the email is invalid it throws an exception`() {
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
    fun `when the adress is less than 10 characters it throws an exception`() {
        val adress = "fails"
        assertThrows<InvalidAdressException> { UserRegisterValidator.validateAdress(adress) }
    }

    @Test
    fun `when the address is more than 30 characters it throws an exception`() {
        val adress = "agnklñasjngñkaglkangslanalnhaln"
        assertThrows<InvalidAdressException> { UserRegisterValidator.validateAdress(adress) }
    }



}