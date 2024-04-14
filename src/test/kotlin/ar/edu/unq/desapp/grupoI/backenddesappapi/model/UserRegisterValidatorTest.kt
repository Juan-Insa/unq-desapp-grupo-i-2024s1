package ar.edu.unq.desapp.grupoI.backenddesappapi.model

import ar.edu.unq.desapp.grupoI.backenddesappapi.helpers.UserRegisterValidator
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRegisterValidatorTest {

    val userRegisterValidator = UserRegisterValidator()

    @Test
    fun `when the User name is less than 3 characters it throws an exception`() {
        val user = User("a", "lalala", "lalala@hotmail.com", "false street", "User.Test1", "1111111111111111111111", "22222222")

        assertThrows( IllegalArgumentException())
    }
}