package ar.edu.unq.desapp.grupoI.backenddesappapi.service

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.CryptoCurrency
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.Intention
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.OperatedVolume
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Action
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Asset
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Operation
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.OperationState
import ar.edu.unq.desapp.grupoI.backenddesappapi.utils.DataService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
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
    @Autowired lateinit var dataService: DataService

    @MockBean
    private lateinit var cryptoCurrencyService: CryptoCurrencyService
    @MockBean
    private lateinit var binanceProxyService: BinanceProxyService

    lateinit var sellIntention: Intention
    lateinit var buyIntention: Intention
    lateinit var intentionUser: User
    lateinit var interestedUser: User
    lateinit var userName: String

    @BeforeEach
    fun init() {
        val resultadoMockeado = CryptoCurrency("ALICEUSDT", 50.0f, "2022-04-13 12:00:00")
        Mockito.`when`(binanceProxyService.getCryptoCurrency("ALICEUSDT")).thenReturn(resultadoMockeado)


        intentionUser = User(
            name ="intentionUser",
            lastName = "user",
            email = "intentionUser@gmail.com",
            address = "intentionUserAddress",
            password = "Intention.User.Pass",
            cvu = "1234567890123456789012",
            cryptoWalletAddress = "12345678")

        intentionUser = userService.registerUser(intentionUser)

        interestedUser = User(
            name ="interestedUser",
            lastName = "user",
            email = "interestedUser@gmail.com",
            address = "interestedUserAddress",
            password = "Interested.User.Pass",
            cvu = "9876543210987654321098",
            cryptoWalletAddress = "87654321"
        )
        interestedUser = userService.registerUser(interestedUser)

        sellIntention = Intention(
            cryptoAsset = Asset.ALICEUSDT,
            amount = 0.5,
            operation = Operation.SELL,
            price = 49.0
        )
        sellIntention = intentionService.createIntention(sellIntention, intentionUser.id!!)

        buyIntention = Intention(
            cryptoAsset = Asset.ALICEUSDT,
            amount = 0.8,
            operation = Operation.BUY,
            price = 49.0
        )
        buyIntention = intentionService.createIntention(buyIntention, intentionUser.id!!)

    }

    @Test
    fun `createTransaction persist a SELL transaction with valid attributes`() {
        var validTransaction = transactionService.createTransaction(sellIntention.id!!, interestedUser.id!!)

        validTransaction = transactionService.getTransactionById(validTransaction.id!!)

        assertEquals(validTransaction.action, Action.CONFIRMTRANSFER)
        assertEquals(validTransaction.interestedUser.id, interestedUser.id)
        assertEquals(validTransaction.state, OperationState.ACTIVE)
        assertEquals(validTransaction.intention.id, sellIntention.id)
    }

    @Test
    fun `createTransaction persist a BUY transaction wit valid attributes`() {
        var validTransaction = transactionService.createTransaction(buyIntention.id!!, interestedUser.id!!)

        validTransaction = transactionService.getTransactionById(validTransaction.id!!)

        assertEquals(validTransaction.action, Action.TRANSFER)
        assertEquals(validTransaction.interestedUser.id, interestedUser.id)
        assertEquals(validTransaction.state, OperationState.ACTIVE)
        assertEquals(validTransaction.intention.id, buyIntention.id)
    }
    @Test
    fun `getOperatedVolume for all transactions of interested user`() {
        Mockito.`when`(cryptoCurrencyService.getCurrencyValue(anyString())).thenReturn(10.0f)
        val transaction = transactionService.createTransaction(sellIntention.id!!, interestedUser.id!!)
        val transactions: OperatedVolume = transactionService.getOperatedVolumeFor(interestedUser.id!!, "19/05/2024 15:30:00", "19/05/2024 23:30:00")

        assertEquals(49.0, transaction.intention.price)
        assertEquals(0.5, transaction.intention.amount)

        assertEquals(24.5, transactions.totalAmountUSD)
    }
    @AfterEach
    fun deleteAll() {
        dataService.deleteAll()
    }
}