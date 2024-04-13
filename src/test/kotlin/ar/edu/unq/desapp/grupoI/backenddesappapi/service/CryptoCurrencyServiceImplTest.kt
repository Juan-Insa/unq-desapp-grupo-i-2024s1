package ar.edu.unq.desapp.grupoI.backenddesappapi.service

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.CryptoCurrency
import ar.edu.unq.desapp.grupoI.backenddesappapi.helpers.CurrentDateTime
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Currency
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@ExtendWith(SpringExtension::class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CryptoCurrencyServiceImplTest {

    @Autowired lateinit var cryptoCurrencyService: CryptoCurrencyService

    val aliceUSDT = Currency.ALICEUSDT
    val formatter = CurrentDateTime.getNewDateFormatter()

    @Test
    fun `creating a CryptoCurrency it adds an id`(){
        val cryptoCurrency = CryptoCurrency("ALICEUSDT", 60.10f, formatter.format(Date()))

        assertNull(cryptoCurrency.id)

        cryptoCurrencyService.createCryptoCurrency(cryptoCurrency)

        assertNotNull(cryptoCurrency.id)
    }

    @Test
    fun `get the current market price of ALICEUSDT currency`(){
        val result = cryptoCurrencyService.getCurrencyValue(aliceUSDT.toString())
    }
}
