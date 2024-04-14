package ar.edu.unq.desapp.grupoI.backenddesappapi.service

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.CryptoCurrency
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.CryptoCurrency24hr
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
    @Test
    fun `get quotes from last 24 hours of ALICEUSDT`() {
        val resultadoMockeado = CryptoCurrency24hr(
            symbol = "ALICEUSDT",
            priceChange = "-0.18500000",
            priceChangePercent = "-13.941",
            weightedAvgPrice = "1.18709191",
            prevClosePrice = "1.32700000",
            lastPrice = "1.14200000",
            lastQty = "216.24000000",
            bidPrice = "1.13900000",
            bidQty = "3545.82000000",
            askPrice = "1.14100000",
            askQty = "183.54000000",
            openPrice = "1.32700000",
            highPrice = "1.38900000",
            lowPrice = "1.00000000",
            volume = "7473890.80000000",
            quoteVolume = "8872195.31346000",
            openTime = 1712973714376,
            closeTime = 1713060114376,
            firstId = 90221045,
            lastId = 90300230,
            count = 79186
        )
        Mockito.`when`(binanceProxyService.getCrypto24hrCurrencyValue(resultadoMockeado.symbol))
            .thenReturn(resultadoMockeado)
        val resultado = cryptoCurrencyService.get24hrCurrencyQuoteFrom("ALICEUSDT")

        assertEquals(resultadoMockeado, resultado)
        assertEquals(resultadoMockeado.symbol, resultado?.symbol)
        assertEquals(resultadoMockeado.priceChange, resultado?.priceChange)
    }
}
