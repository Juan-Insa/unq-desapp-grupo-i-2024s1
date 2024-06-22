package ar.edu.unq.desapp.grupoI.backenddesappapi.service

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoI.backenddesappapi.utils.DataService
import org.junit.jupiter.api.AfterEach
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
    @Autowired lateinit var dataService: DataService

    @Test
    fun `registerUser fails when trying to register user with invalid password`(){
        val user = User(
            name = "juancho",
            lastName = "insa",
            email = "juancho@gmail.com",
            address = "validStreetAddress",
            password = "xxxxx",
            cvu = "1234567890123456789012",
            cryptoWalletAddress = "12345678"
        )
        assertThrows<IllegalArgumentException> {
            userService.registerUser(user)
        }
    }

    @Test
    fun `registerUser persist the user with valid credentials`(){
        val user = User(
            name = "juancho",
            lastName = "insa",
            email = "juancho@gmail.com",
            address = "validStreetAddress",
            password = "Valid.Password",
            cvu = "1234567890123456789012",
            cryptoWalletAddress = "12345678"
        )
        userService.registerUser(user)

        val userObtained = userService.getUserByEmail("juancho@gmail.com")

        assertEquals("juancho", userObtained.name)
        assertEquals("insa", userObtained.lastName)
        assertEquals("juancho@gmail.com", userObtained.email)
        assertEquals("validStreetAddress", userObtained.address)
        assertEquals("Valid.Password", userObtained.temporaryPassword)
        assertEquals("1234567890123456789012", userObtained.cvu)
        assertEquals("12345678", userObtained.cryptoWalletAddress)
    }

    @AfterEach
    fun deleteAll() {
        dataService.deleteAll()
    }
}