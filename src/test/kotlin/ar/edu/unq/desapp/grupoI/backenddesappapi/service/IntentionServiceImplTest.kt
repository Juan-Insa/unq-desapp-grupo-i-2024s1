package ar.edu.unq.desapp.grupoI.backenddesappapi.service

import ar.edu.unq.desapp.grupoI.backenddesappapi.exceptions.InvalidIntentionPriceException
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.CryptoCurrency
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.Intention
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Asset
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Operation
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
    @Autowired lateinit var userService: UserService
    @Autowired lateinit var dataService: DataService

    @MockBean
    private lateinit var binanceProxyService: BinanceProxyService

    lateinit var intention: Intention
    lateinit var intentionUser: User

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
    }

    @Test
    fun `createIntention persist the intention with valid attributes`() {
        var validIntention = intentionService.createIntention(
            user = intentionUser,
            cryptoAsset = Asset.ALICEUSDT,
            amount = 0.5,
            operation = Operation.SELL,
            price = 52.0
        )

        validIntention = intentionService.getIntentionById(validIntention.id!!)

        Assertions.assertEquals(validIntention.cryptoAsset, Asset.ALICEUSDT)
        Assertions.assertEquals(validIntention.amount, 0.5)
        Assertions.assertEquals(validIntention.price, 52.0)
        Assertions.assertEquals(validIntention.operation, Operation.SELL)
        Assertions.assertEquals(validIntention.userEmail, intentionUser.email)
    }

    @Test
    fun `createIntention throws InvalidIntentionPriceException when price validation fails`() {
        assertThrows<InvalidIntentionPriceException> {
            intentionService.createIntention(
                user = intentionUser,
                cryptoAsset = Asset.ALICEUSDT,
                amount = 0.5,
                operation = Operation.SELL,
                price = 60.0
            )
        }
    }

    @AfterEach
    fun deleteAll() {
        dataService.deleteAll()
    }
}