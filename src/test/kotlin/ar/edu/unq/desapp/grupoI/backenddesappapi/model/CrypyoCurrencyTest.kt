package ar.edu.unq.desapp.grupoI.backenddesappapi.model

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Currency
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test;
import java.util.*

class CrypyoCurrencyTest {

    val formatter = CurrentDateTime.getNewDateFormatter()
    @Test
    fun `Create a crypto currency with symbol and price`() {
        val date = formatter.format(Date())
        val aliceCrypto = CryptoCurrency("ALICEUSDT", 60.10f, date)
        assertEquals(aliceCrypto.symbol, "ALICEUSDT")
        assertEquals(aliceCrypto.marketPrice, 60.10f)
        assertEquals(aliceCrypto.lastUpdateDateAndTime, date)
    }
    @Test
    fun `Create a crypto currency and add to crypto list`() {
        val cryptoList = CryptoCurrencyList();
        val date = formatter.format(Date())
        val aliceCrypto = CryptoCurrency("ALICEUSDT", 60.10f, date)
        cryptoList.addCrypto(aliceCrypto);

        assertTrue(cryptoList.cryptos.contains(aliceCrypto));
        }
}