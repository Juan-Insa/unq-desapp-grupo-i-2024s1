package ar.edu.unq.desapp.grupoI.backenddesappapi.webservice.controllers

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.CryptoCurrency
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.Intention
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Asset
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Operation
import ar.edu.unq.desapp.grupoI.backenddesappapi.persistence.repository.IntentionRepository
import ar.edu.unq.desapp.grupoI.backenddesappapi.service.BinanceProxyService
import ar.edu.unq.desapp.grupoI.backenddesappapi.service.IntentionService
import ar.edu.unq.desapp.grupoI.backenddesappapi.webservice.controllers.dto.LoginUserDTO
import ar.edu.unq.desapp.grupoI.backenddesappapi.webservice.controllers.request.UserRequest
import org.mockito.Mockito
import org.springframework.boot.test.mock.mockito.MockBean

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.OperationState
import ar.edu.unq.desapp.grupoI.backenddesappapi.persistence.repository.TransactionRepository
import ar.edu.unq.desapp.grupoI.backenddesappapi.persistence.repository.UserRepository
import ar.edu.unq.desapp.grupoI.backenddesappapi.webservice.controllers.request.TransactionRequest
import com.fasterxml.jackson.databind.ObjectMapper
import org.json.JSONObject
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = ["classpath:application-integrationtest.properties"])
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TransactionControllerTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var transactionRepository: TransactionRepository

    @Autowired
    private lateinit var intentionRepository: IntentionRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var authenticationController: AuthenticationController

    @Autowired
    private lateinit var intentionService: IntentionService

    @MockBean
    private lateinit var binanceProxyService: BinanceProxyService

    lateinit var sellUser: User
    lateinit var buyUser: User
    lateinit var tokenSell: String
    lateinit var tokenBuy: String
    lateinit var intentionSell: Intention
    lateinit var intentionBuy: Intention

    @BeforeEach
    fun cleanUp() {
        transactionRepository.deleteAll()
        intentionRepository.deleteAll()
    }
    @BeforeAll
    fun setup() {
        sellUser = createUserAndRegister("testSellUser")
        buyUser = createUserAndRegister("testBuyUser")
        tokenSell = authenticationController.authenticate(LoginUserDTO(sellUser.email, sellUser.password)).body?.token!!
        tokenBuy = authenticationController.authenticate(LoginUserDTO(buyUser.email, buyUser.password)).body?.token!!
    }
    @Test
    fun whenPostNewTransaction_thenStatus200() {
        createTransaction()
            .andExpect(status().isOk)
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.interestedUserEmail").value(buyUser.email))
    }

    @Test
    fun whenGetTransactionById_thenStatus200() {
        val response = createTransaction().andReturn().response.contentAsString
        val jsonObject = JSONObject(response)
        val transId = jsonObject.get("id")
        mvc.perform(
            get("/api/transaction/${transId}")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer $tokenBuy")
        )
            .andExpect(status().isOk)
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(transId))
    }
    @Test
    fun whenPutFinishTransaction_thenStatus200() {
        val response = createTransaction().andReturn().response.contentAsString
        val jsonObject = JSONObject(response)
        val transId = jsonObject.get("id")
        mvc.perform(
            put("/api/transaction/${transId}/finish")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer $tokenBuy")
        )
            .andExpect(status().isOk)
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.state").value(OperationState.INACTIVE.name))
    }

    @Test
    fun whenPutCancelTransaction_thenStatus200() {
        val response = createTransaction().andReturn().response.contentAsString
        val jsonObject = JSONObject(response)
        val transId = jsonObject.get("id")
        mvc.perform(
            put("/api/transaction/${transId}/${buyUser.id}/cancel")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer $tokenBuy")
        )
            .andExpect(status().isOk)
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.state").value(OperationState.INACTIVE.name))
    }

    fun createTestIntentionBy(user: User, operation: Operation): Intention {
        val intention = Intention(
            user = user,
            operation = operation,
            cryptoAsset = Asset.BTCUSDT,
            amount = 10.0,
            price = 60000.00
        )
        val resultadoMockeado = CryptoCurrency("BTCUSDT", 60000.0f, "2022-06-26 12:00:00")

        Mockito.`when`(binanceProxyService.getCryptoCurrency("BTCUSDT")).thenReturn(resultadoMockeado)
        return intentionService.createIntention(intention, user.id!!)
    }
    fun createTransaction(): ResultActions {
        intentionSell = createTestIntentionBy(sellUser, Operation.SELL)
        intentionBuy = createTestIntentionBy(buyUser, Operation.BUY)
        val transactionRequest = TransactionRequest(intentionSell.id!!, buyUser.id!!)

        return mvc.perform(
            post("/api/transaction/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transactionRequest))
                .header("Authorization", "Bearer $tokenBuy")
        )
    }
    fun createUserAndRegister(name: String): User {
        var intentionUser = User(
            name = name,
            lastName = "user",
            email = "$name@gmail.com",
            address = "intentionUserAddress",
            password = "Intention.User.Pass",
            cvu = "1234567890123456789012",
            cryptoWalletAddress = "12345678")
        val registeredUser = authenticationController.registerUser(UserRequest(intentionUser)).body!!
        intentionUser.id = registeredUser.id
        return intentionUser
    }
    @AfterAll
    fun clean() {
        transactionRepository.deleteAll()
        intentionRepository.deleteAll()
        userRepository.deleteAll()
    }
}
