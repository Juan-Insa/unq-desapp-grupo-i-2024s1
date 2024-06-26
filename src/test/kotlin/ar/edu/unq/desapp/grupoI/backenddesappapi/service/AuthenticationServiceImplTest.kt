package ar.edu.unq.desapp.grupoI.backenddesappapi.service

import ar.edu.unq.desapp.grupoI.backenddesappapi.exceptions.UserNotFoundException
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoI.backenddesappapi.utils.DataService
import ar.edu.unq.desapp.grupoI.backenddesappapi.webservice.controllers.dto.LoginUserDTO
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.lang.IllegalArgumentException

@ExtendWith(SpringExtension::class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthenticationServiceImplTest {

    @Autowired lateinit var authenticationService: AuthenticationService
    @Autowired lateinit var userService: UserService
    @Autowired lateinit var dataService: DataService
    @Autowired lateinit var passwordEncoder: PasswordEncoder

    @Test
    fun `signup fails when trying to register user with invalid password`(){
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
            authenticationService.signup(user)
        }
    }

    @Test
    fun `signup persist the user with valid credentials`(){
        val user = User(
            name = "juancho",
            lastName = "insa",
            email = "juancho@gmail.com",
            address = "validStreetAddress",
            password = "Valid.Password",
            cvu = "1234567890123456789012",
            cryptoWalletAddress = "12345678"
        )
        authenticationService.signup(user)

        val userObtained = userService.getUserByEmail("juancho@gmail.com")

        assertEquals("juancho", userObtained.name)
        assertEquals("insa", userObtained.lastName)
        assertEquals("juancho@gmail.com", userObtained.email)
        assertEquals("validStreetAddress", userObtained.address)
        assertTrue(passwordEncoder.matches("Valid.Password", userObtained.password))
        assertEquals("1234567890123456789012", userObtained.cvu)
        assertEquals("12345678", userObtained.cryptoWalletAddress)
        assertEquals(0, userObtained.reputation)
        assertEquals(0, userObtained.operations)
    }

    @Test
    fun `signup throws exception when the user is already registered`() {
        val user = User(
            name = "juancho",
            lastName = "insa",
            email = "juancho@gmail.com",
            address = "validStreetAddress",
            password = "Valid.Password",
            cvu = "1234567890123456789012",
            cryptoWalletAddress = "12345678"
        )
        authenticationService.signup(user)

        assertThrows<IllegalArgumentException> { authenticationService.signup(user) }
    }

    @Test
    fun `authenticating a registered user returns the corresponding user`() {
        val user = User(
            name = "juancho",
            lastName = "insa",
            email = "juancho@gmail.com",
            address = "validStreetAddress",
            password = "Valid.Password",
            cvu = "1234567890123456789012",
            cryptoWalletAddress = "12345678"
        )
        authenticationService.signup(user)

        val logingUserDTO = LoginUserDTO("juancho@gmail.com", "Valid.Password")

        val loggedUser = authenticationService.authenticate(logingUserDTO)

        assertEquals("juancho", loggedUser.name)
        assertEquals("insa", loggedUser.lastName)
        assertEquals("juancho@gmail.com", loggedUser.email)
        assertEquals("validStreetAddress", loggedUser.address)
        assertTrue(passwordEncoder.matches("Valid.Password", loggedUser.password))
        assertEquals("1234567890123456789012", loggedUser.cvu)
        assertEquals("12345678", loggedUser.cryptoWalletAddress)
        assertEquals(0, loggedUser.reputation)
        assertEquals(0, loggedUser.operations)
    }

    @Test
    fun `authenticating an unregistered user throws an exception`() {
        val logingUserDTO = LoginUserDTO("juancho@gmail.com", "Valid.Password")

        assertThrows<BadCredentialsException> { authenticationService.authenticate(logingUserDTO) }
    }

    @AfterEach
    fun deleteAll() {
        dataService.deleteAll()
    }
}