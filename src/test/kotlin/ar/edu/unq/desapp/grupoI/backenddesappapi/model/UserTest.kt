package ar.edu.unq.desapp.grupoI.backenddesappapi.model

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Asset
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Operation
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserTest {

    lateinit var validUser: User

    @BeforeEach
    fun init() {
        validUser = User(
            name ="juancho",
            lastName = "insa",
            email = "lala@gmail.com",
            address = "validStreetAddress",
            password = "Valid.Password",
            cvu = "1234567890123456789012",
            cryptoWalletAddress = "12345678")
    }
}