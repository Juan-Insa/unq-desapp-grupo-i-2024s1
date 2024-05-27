package ar.edu.unq.desapp.grupoI.backenddesappapi.service

import ar.edu.unq.desapp.grupoI.backenddesappapi.helpers.CurrentDateTime.getNewLocalDateTime
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
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@ExtendWith(SpringExtension::class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TransactionServiceImplTest {

    @SpyBean lateinit var transactionService: TransactionService
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

    @BeforeEach
    fun init() {
        val resultadoMockeado = CryptoCurrency("ALICEUSDT", 50.0f, "2022-04-13 12:00:00")
        Mockito.doReturn(resultadoMockeado).`when`(binanceProxyService).getCryptoCurrency("ALICEUSDT")


        intentionUser = User(
            name ="intentionUser",
            lastName = "user",
            email = "intentionUser@gmail.com",
            address = "intentionUserAddress",
            password = "Intention.User.Pass",
            cvu = "1234567890123456789012",
            cryptoWalletAddress = "12345678")
        intentionUser.reputation = 50
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
        interestedUser.reputation = 80
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

        //transactionServiceSpy = Mockito.spy(transactionService)
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
    fun `canceling a transaction sets the transaction action and state to CANCEL and INACTIVE`() {
        var validTransaction = transactionService.createTransaction(buyIntention.id!!, interestedUser.id!!)

        val canceledTransaction = transactionService.cancelTransaction(validTransaction.id!!, interestedUser.id!!)

        assertEquals(canceledTransaction.action, Action.CANCEL)
        assertEquals(canceledTransaction.interestedUser.id, interestedUser.id)
        assertEquals(canceledTransaction.state, OperationState.INACTIVE)
        assertEquals(canceledTransaction.intention.id, buyIntention.id)
    }

    @Test
    fun `canceling a transaction takes 20 reputation points from the canceling user`() {
        var validTransaction = transactionService.createTransaction(buyIntention.id!!, interestedUser.id!!)

        transactionService.cancelTransaction(validTransaction.id!!, interestedUser.id!!)

        val cancelingUser = userService.getUserById(interestedUser.id!!)

        assertEquals(60, cancelingUser.reputation)
    }

    @Test
    fun `finishing a transaction within the first 30 min adds users involved 10 reputation points`() {
        var validTransaction = transactionService.createTransaction(buyIntention.id!!, interestedUser.id!!)

        transactionService.finishTransaction(validTransaction.id!!)

        intentionUser = userService.getUserById(intentionUser.id!!)
        interestedUser = userService.getUserById(interestedUser.id!!)

        assertEquals(60, intentionUser.reputation)
        assertEquals(90, interestedUser.reputation)
    }

    @Test
    fun `finishing a transaction after the first 30 min adds users involved 5 reputation points`() {
        var validTransaction = transactionService.createTransaction(buyIntention.id!!, interestedUser.id!!)

        val date = validTransaction.initTime

        Mockito.`when`(transactionService.isPast30Minutes(date)).thenReturn(true)

        transactionService.finishTransaction(validTransaction.id!!)

        intentionUser = userService.getUserById(intentionUser.id!!)
        interestedUser = userService.getUserById(interestedUser.id!!)

        assertEquals(55, intentionUser.reputation)
        assertEquals(85, interestedUser.reputation)
    }

    @Test
    fun `finishing a transaction sets its state to inactive`() {
        var validTransaction = transactionService.createTransaction(buyIntention.id!!, interestedUser.id!!)

        val finishedTransaction = transactionService.finishTransaction(validTransaction.id!!)

        assertEquals(OperationState.INACTIVE, finishedTransaction.state)
    }

    @Test
    fun `getOperatedVolume for all transactions of interested user`() {
        Mockito.`when`(cryptoCurrencyService.getCurrencyValue(anyString())).thenReturn(10.0f)
        val transaction = transactionService.createTransaction(sellIntention.id!!, interestedUser.id!!)
        val transactions: OperatedVolume = transactionService.getOperatedVolumeFor(interestedUser.id!!, getNewLocalDateTime(), getNewLocalDateTime().plusHours(1))

        assertEquals(49.0, transaction.intention.price)
        assertEquals(0.5, transaction.intention.amount)

        assertEquals(24.5, transactions.totalAmountUSD)
    }

    @AfterEach
    fun deleteAll() {
        dataService.deleteAll()
    }
}