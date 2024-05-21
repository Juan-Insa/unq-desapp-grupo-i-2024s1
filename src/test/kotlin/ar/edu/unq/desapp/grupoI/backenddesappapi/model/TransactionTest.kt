package ar.edu.unq.desapp.grupoI.backenddesappapi.model

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Action
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Asset
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Operation
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.OperationState
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TransactionTest {

    lateinit var intentionUser: User
    lateinit var interestedUser: User
    lateinit var transaction: Transaction
    lateinit var intention: Intention

    @BeforeEach
    fun init() {
        intentionUser = User(
            name ="juancho",
            lastName = "insa",
            email = "lala@gmail.com",
            address = "validStreetAddress",
            password = "Valid.Password",
            cvu = "1234567890123456789012",
            cryptoWalletAddress = "12345678")

        interestedUser = User(
            name ="peter",
            lastName = "parker",
            email = "pp@gmail.com",
            address = "validStreetAddress",
            password = "Valid.Password",
            cvu = "1234567890123456789012",
            cryptoWalletAddress = "12345678")

        intention = Intention(
            cryptoAsset = Asset.ALICEUSDT,
            amount = 0.5,
            operation = Operation.SELL,
            price = 60.0
        )

        intention.user = intentionUser

        transaction = Transaction(interestedUser, Action.TRANSFER)
        transaction.intention = intention
    }

    @Test
    fun `changeAction modifies the transaction action`() {
        transaction.changeAction(Action.CANCEL)

        Assertions.assertEquals(Action.CANCEL, transaction.action)
    }

    @Test
    fun `changeState modifies modifies the transaction state`() {
        transaction.changeState(OperationState.INACTIVE)

        Assertions.assertEquals(OperationState.INACTIVE, transaction.state)
    }

    @Test
    fun `intentionUser returns the transaction intention user`() {
        val intentionUser = transaction.intentionUser()

        Assertions.assertEquals(0, intentionUser.reputation)
        Assertions.assertEquals("juancho", intentionUser.name)
        Assertions.assertEquals("insa", intentionUser.lastName)
        Assertions.assertEquals("lala@gmail.com", intentionUser.email)
        Assertions.assertEquals("validStreetAddress", intentionUser.address)
        Assertions.assertEquals("Valid.Password", intentionUser.password)
        Assertions.assertEquals("12345678", intentionUser.cryptoWalletAddress)
        Assertions.assertEquals("1234567890123456789012", intentionUser.cvu)
    }
}
