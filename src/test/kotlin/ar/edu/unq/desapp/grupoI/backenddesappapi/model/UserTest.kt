package ar.edu.unq.desapp.grupoI.backenddesappapi.model

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Asset
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Operation
import org.junit.jupiter.api.Assertions.*
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
            criptoWalletAddress = "12345678")
    }


    @Test
    fun `when a User posts an intention it gets added to the ActiveIntentions list`() {
        val asset = Asset.ALICEUSDT
        val amount = 0.2
        val price = 20000.0
        val priceInPesos = 50000.0
        val operation = Operation.SELL

        val intention = validUser.postIntent(asset, amount, price, priceInPesos, operation)

        assertTrue { ActiveIntentions.intentions.contains(intention) }
    }
}