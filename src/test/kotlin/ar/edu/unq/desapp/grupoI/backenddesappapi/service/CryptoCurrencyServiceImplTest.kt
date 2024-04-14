package ar.edu.unq.desapp.grupoI.backenddesappapi.service

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.CryptoCurrency
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.CurrentDateTime
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.CryptoCurrencyEnum
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@ExtendWith(SpringExtension::class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CryptoCurrencyServiceImplTest {

    @Autowired lateinit var cryptoCurrencyService: CryptoCurrencyService

    @MockBean
    private lateinit var binanceProxyService: BinanceProxyService


    val aliceUSDT = CryptoCurrencyEnum.ALICEUSDT

    @Test
    fun `creating a CryptoCurrency it adds an id`(){
        val cryptoCurrency = CryptoCurrency("ALICEUSDT", 60.10f, "2022-04-13 12:00:00")

        assertNull(cryptoCurrency.id)

        cryptoCurrencyService.createCryptoCurrency(cryptoCurrency)

        assertNotNull(cryptoCurrency.id)
    }

    @Test
    fun `get the current market price of ALICEUSDT currency`(){
        val resultadoMockeado = CryptoCurrency("ALICEUSDT", 50.0f, "2022-04-13 12:00:00")

        Mockito.`when`(binanceProxyService.getCryptoCurrencyValue("ALICEUSDT")).thenReturn(resultadoMockeado)
        val resultado = cryptoCurrencyService.getCurrencyValue(aliceUSDT.toString())

        assertNotNull(resultado)
        assertEquals("ALICEUSDT",resultado?.symbol)
        assertEquals(50.0f, resultado?.marketPrice)
        assertEquals(resultado, resultadoMockeado)
    }


}
