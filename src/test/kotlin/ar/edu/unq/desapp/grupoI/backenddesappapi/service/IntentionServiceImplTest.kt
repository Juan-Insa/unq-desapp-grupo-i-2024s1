package ar.edu.unq.desapp.grupoI.backenddesappapi.service

import ar.edu.unq.desapp.grupoI.backenddesappapi.exceptions.InvalidIntentionPriceException
import ar.edu.unq.desapp.grupoI.backenddesappapi.exceptions.UserNotFoundException
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.CryptoCurrency
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.Intention
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Asset
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Operation
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.OperationState
import ar.edu.unq.desapp.grupoI.backenddesappapi.utils.DataService
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IntentionServiceImplTest {


    @Autowired lateinit var intentionService: IntentionService
    @Autowired lateinit var authenticationService: AuthenticationService
    @Autowired lateinit var dataService: DataService

    @MockBean
    private lateinit var binanceProxyService: BinanceProxyService

    lateinit var intention: Intention
    lateinit var intentionUser: User


    @BeforeEach
    fun init() {
        dataService.createTestData()

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

        intentionUser = authenticationService.signup(intentionUser)
    }

    @Test
    fun `createIntention persist the intention with valid attributes`() {
        val intention = Intention(
            cryptoAsset = Asset.ALICEUSDT,
            amount = 0.5,
            operation = Operation.SELL,
            price = 52.0)

        var validIntention = intentionService.createIntention(intention, intentionUser.id!!)

        validIntention = intentionService.getIntentionById(validIntention.id!!)

        Assertions.assertEquals(validIntention.cryptoAsset, Asset.ALICEUSDT)
        Assertions.assertEquals(validIntention.amount, 0.5)
        Assertions.assertEquals(validIntention.price, 52.0)
        Assertions.assertEquals(validIntention.operation, Operation.SELL)
        Assertions.assertEquals(validIntention.state, OperationState.ACTIVE)
        Assertions.assertEquals(validIntention.user!!.id, intentionUser.id)
    }

    @Test
    fun `createIntention throws InvalidIntentionPriceException when price validation fails`() {
        val intention = Intention(
            cryptoAsset = Asset.ALICEUSDT,
            amount = 0.5,
            operation = Operation.SELL,
            price = 60.0
        )

        assertThrows<InvalidIntentionPriceException> {
            intentionService.createIntention(intention, intentionUser.id!!)
        }
    }

    @Test
    fun `returns all the intentions from the dataset`() {
        val intentions = intentionService.getAllIntentions()

        Assertions.assertEquals(7, intentions.size)
    }

    @Test
    fun `returns all the active intentions from the dataset`() {
        var intention = Intention(
            cryptoAsset = Asset.ALICEUSDT,
            amount = 0.5,
            operation = Operation.SELL,
            price = 52.0
        )
        intention = intentionService.createIntention(intention, intentionUser.id!!)
        intention.state = OperationState.INACTIVE
        intentionService.saveIntention(intention)

        val intentions = intentionService.getActiveIntentions()

        Assertions.assertEquals(5, intentions.size)
    }

    @Test
    fun `returns all the active intentions from an user`() {
        var intention1 = Intention(
            cryptoAsset = Asset.ALICEUSDT,
            amount = 0.5,
            operation = Operation.SELL,
            price = 50.0
        )
        var intention2 = Intention(
            cryptoAsset = Asset.ALICEUSDT,
            amount = 1.0,
            operation = Operation.BUY,
            price = 50.0
        )

        intention1 = intentionService.createIntention(intention1, intentionUser.id!!)
        intention2 = intentionService.createIntention(intention2, intentionUser.id!!)

        val userActiveIntentions = intentionService.getActiveIntentionsFrom(intentionUser.id!!)

        Assertions.assertEquals(2, userActiveIntentions.size)
        Assertions.assertEquals(intention1.id, userActiveIntentions[0].id)
        Assertions.assertEquals(intention2.id, userActiveIntentions[1].id)
    }

    @Test
    fun `trying to get all the active intentions from an invalid user throws an error`() {
        assertThrows<UserNotFoundException> {
            intentionService.getActiveIntentionsFrom(121232)
        }
    }


    @AfterEach
    fun deleteAll() {
        dataService.deleteAll()
    }
}