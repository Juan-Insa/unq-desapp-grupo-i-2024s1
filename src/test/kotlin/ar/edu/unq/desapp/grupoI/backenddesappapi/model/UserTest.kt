package ar.edu.unq.desapp.grupoI.backenddesappapi.model

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.Assertions.assertEquals

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

    @Test
    fun `modifyReputation modifies the user reputation`() {
        validUser.modifyReputation({ a, b -> a + b}, 10)
        assertEquals(10, validUser.reputation)
    }

    @Test
    fun `when adding points the reputation does not get higher than 100`() {
        validUser.modifyReputation({ a, b -> a + b}, 120)
        assertEquals(100, validUser.reputation)
    }

    @Test
    fun `when reducing points the reputation does not get lower than 0`() {
        validUser.modifyReputation({ a, b -> a - b}, 20)
        assertEquals(0, validUser.reputation)
    }
}