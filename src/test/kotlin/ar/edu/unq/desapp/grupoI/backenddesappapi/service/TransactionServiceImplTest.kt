package ar.edu.unq.desapp.grupoI.backenddesappapi.service

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.CryptoCurrency
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.Intention
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Action
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Asset
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Operation
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TransactionServiceImplTest {

    @Autowired lateinit var transactionService: TransactionService
    @Autowired lateinit var intentionService: IntentionService
    @Autowired lateinit var userService: UserService
    @Autowired lateinit var cryptoCurrencyService: CryptoCurrencyService

    @MockBean
    private lateinit var binanceProxyService: BinanceProxyService

    lateinit var intention: Intention
    lateinit var intentionUser: User
    lateinit var interestedUser: User
    lateinit var userName: String

    @BeforeEach
    fun init() {
        val resultadoMockeado = CryptoCurrency("ALICEUSDT", 50.0f, "2022-04-13 12:00:00")
        Mockito.`when`(binanceProxyService.getCryptoCurrency("ALICEUSDT")).thenReturn(resultadoMockeado)

        intentionUser = userService.registerUser(
            name ="intentionUser",
            lastName = "user",
            email = "intentionUser@gmail.com",
            address = "intentionUserAddress",
            password = "Intention.User.Pass",
            cvu = "1234567890123456789012",
            cryptoWalletAddress = "12345678"
        )

        interestedUser = userService.registerUser(
            name ="interestedUser",
            lastName = "user",
            email = "interestedUser@gmail.com",
            address = "interestedUserAddress",
            password = "Interested.User.Pass",
            cvu = "9876543210987654321098",
            cryptoWalletAddress = "87654321"
        )
        userName = intentionUser.name + " " + intentionUser.lastName

        intention = intentionService.createIntention(
            userName = userName,
            userEmail = intentionUser.email,
            cryptoAsset = Asset.ALICEUSDT,
            amount = 0.5,
            operation = Operation.SELL,
            price = 49.0
        )

    }

    @Test
    fun `createTransaction persist the transaction with valid attributes`() {
        var validTransaction = transactionService.createTransaction(intention, interestedUser)

        validTransaction = transactionService.getTransactionById(validTransaction.id!!)

        assertEquals(validTransaction.action, Action.AWAITING)
        assertEquals(validTransaction.cryptoAsset, Asset.ALICEUSDT)
        assertEquals(validTransaction.destinationAddress, intentionUser.cvu)
        assertEquals(validTransaction.nominalAmount, 0.5)
        assertEquals(validTransaction.cryptoCurrencyPrice, 49.0)
        assertEquals(validTransaction.numberOfOperations, interestedUser.operations)
        assertEquals(validTransaction.userEmail, interestedUser.email)
        assertEquals(validTransaction.reputation, interestedUser.reputation)
    }
}