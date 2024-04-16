package ar.edu.unq.desapp.grupoI.backenddesappapi.service

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.User
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.lang.IllegalArgumentException

@ExtendWith(SpringExtension::class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceImplTest {

    @Autowired lateinit var userService: UserService

    @Test
    fun `registerUser fails when trying to register user with invalid password`(){
        val user = User(
            "juancho",
            "insa",
            "lala@gmail.com",
            "street123",
            "xxxxx",
            "1234567890123456789012",
            "12345678")

        assertThrows<IllegalArgumentException> { userService.registerUser(user) }
    }

    @Test
    fun `registerUser persist the user with valid credentials`(){
        val user = User(
            "juancho",
            "insa",
            "lala@gmail.com",
            "validStreetAddress",
            "Valid.Password",
            "1234567890123456789012",
            "12345678")

        userService.registerUser(user)

        val userObtained = userService.getUserByName("juancho")

        assertEquals("juancho", userObtained.name)
    }
}