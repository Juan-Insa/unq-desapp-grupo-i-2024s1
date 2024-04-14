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
    @Test
    fun `get current market price of list of currencies`() {
        val resultadosMockeados = listOf(
            CryptoCurrency("ALICEUSDT", 50.0f, "2022-04-13 12:00:00"),
            CryptoCurrency("MATICUSDT", 60.0f, "2022-04-13 12:00:00"),
            CryptoCurrency("AXSUSDT", 70.0f, "2022-04-13 12:00:00"),
            CryptoCurrency("AAVEUSDT", 80.0f, "2022-04-13 12:00:00"),
            CryptoCurrency("ATOMUSDT", 90.0f, "2022-04-13 12:00:00"),
            CryptoCurrency("NEOUSDT", 100.0f, "2022-04-13 12:00:00"),
            CryptoCurrency("DOTUSDT", 110.0f, "2022-04-13 12:00:00"),
            CryptoCurrency("ETHUSDT", 120.0f, "2022-04-13 12:00:00"),
            CryptoCurrency("CAKEUSDT", 130.0f, "2022-04-13 12:00:00"),
            CryptoCurrency("BTCUSDT", 140.0f, "2022-04-13 12:00:00"),
            CryptoCurrency("BNBUSDT", 150.0f, "2022-04-13 12:00:00"),
            CryptoCurrency("ADAUSDT", 160.0f, "2022-04-13 12:00:00"),
            CryptoCurrency("TRXUSDT", 170.0f, "2022-04-13 12:00:00"),
            CryptoCurrency("AUDIOUSDT", 180.0f, "2022-04-13 12:00:00")
        )

        resultadosMockeados.forEachIndexed { index, resultadoMockeado ->
            Mockito.`when`(binanceProxyService.getCryptoCurrencyValue(resultadoMockeado.symbol))
                .thenReturn(resultadoMockeado)
        }

        val resultado = cryptoCurrencyService.getAllCurrencyValues().getList()

        resultadosMockeados.forEachIndexed { index, resultadoMockeado ->
            assertEquals(resultadoMockeado, resultado[index])
        }
    }

}
