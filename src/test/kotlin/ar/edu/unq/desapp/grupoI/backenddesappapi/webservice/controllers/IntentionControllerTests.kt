package ar.edu.unq.desapp.grupoI.backenddesappapi.webservice.controllers

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.Intention
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Asset
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Operation
import ar.edu.unq.desapp.grupoI.backenddesappapi.persistence.repository.IntentionRepository
import ar.edu.unq.desapp.grupoI.backenddesappapi.persistence.repository.TransactionRepository
import ar.edu.unq.desapp.grupoI.backenddesappapi.webservice.controllers.dto.LoginUserDTO
import ar.edu.unq.desapp.grupoI.backenddesappapi.webservice.controllers.request.IntentionRequest
import ar.edu.unq.desapp.grupoI.backenddesappapi.webservice.controllers.request.UserRequest
import com.fasterxml.jackson.databind.ObjectMapper
import org.aspectj.lang.annotation.Before
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.actuate.autoconfigure.wavefront.WavefrontProperties
import org.springframework.test.web.servlet.ResultActions

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = ["classpath:application-integrationtest.properties"])
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IntentionControllerTests {

    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper


    @Autowired
    private lateinit var intentionRepository: IntentionRepository

    @Autowired
    private lateinit var transactionRepository: TransactionRepository


    @Autowired
    private lateinit var authenticationController: AuthenticationController


    lateinit var testSellUser: User
    lateinit var testBuyUser: User
    lateinit var tokenSell: String
    lateinit var tokenBuy: String

    @BeforeEach
    fun cleanUp() {
        transactionRepository.deleteAll()
        intentionRepository.deleteAll()
    }
    @BeforeAll
    fun setup() {
        testSellUser = createUserAndRegister("testSellUser")
        testBuyUser = createUserAndRegister("testBuyUser")
        tokenSell = authenticationController.authenticate(LoginUserDTO(testSellUser.email, testSellUser.password)).body?.token!!
        tokenBuy = authenticationController.authenticate(LoginUserDTO(testBuyUser.email, testBuyUser.password)).body?.token!!
    }
    @Test
    fun whenPostNewIntention_thenStatus200() {
        createTestIntentionBy(testSellUser, Operation.SELL, tokenSell)
            .andExpect(status().isOk)
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.operation", `is`(Operation.SELL.name)))
    }

    @Test
    fun whenGetAllIntentions_thenStatus200() {
        createTestIntentionBy(testBuyUser, Operation.BUY, tokenBuy)
        createTestIntentionBy(testSellUser, Operation.SELL, tokenSell)

        mvc.perform(
            get("/api/intention/all")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer $tokenSell")
        )
            .andExpect(status().isOk)
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].operation", `is`(Operation.BUY.name)))
            .andExpect(jsonPath("$[1].operation", `is`(Operation.SELL.name)))
    }

    @Test
    fun whenGetAllActiveIntentions_thenStatus200() {
        createTestIntentionBy(testBuyUser, Operation.BUY, tokenBuy)
        createTestIntentionBy(testSellUser, Operation.SELL, tokenSell)

        mvc.perform(
            get("/api/intention/active")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer $tokenSell")
        )
            .andExpect(status().isOk)
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].operation", `is`(Operation.BUY.name)))
            .andExpect(jsonPath("$[1].operation", `is`(Operation.SELL.name)))
    }

    @Test
    fun whenGetActiveIntentionsByUserId_thenStatus200() {
        createTestIntentionBy(testBuyUser, Operation.BUY, tokenBuy)
        createTestIntentionBy(testBuyUser, Operation.SELL, tokenBuy)
        val userId = testBuyUser.id!!
        mvc.perform(
            get("/api/intention/user/$userId/active")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer $tokenBuy")
        )
            .andExpect(status().isOk)
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].operation", `is`(Operation.BUY.name)))
            .andExpect(jsonPath("$[1].operation", `is`(Operation.SELL.name)))
    }


    fun createTestIntentionBy(user: User, operation: Operation, token: String): ResultActions {
        val intention = Intention(
            user = user,
            operation = operation,
            cryptoAsset = Asset.BTCUSDT,
            amount = 10.0,
            price = 60478.01
        )
        val intentionRequest = IntentionRequest(
            intention = intention,
            userId = user.id!!
        )
        return mvc.perform(
            post("/api/intention/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(intentionRequest))
                .header("Authorization", "Bearer $token")
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
}
