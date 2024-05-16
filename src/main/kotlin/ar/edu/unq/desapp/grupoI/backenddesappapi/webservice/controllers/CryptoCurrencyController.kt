package ar.edu.unq.desapp.grupoI.backenddesappapi.webservice.controllers

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.CryptoCurrency
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.CryptoCurrency24hr
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.CryptoCurrencyList
import ar.edu.unq.desapp.grupoI.backenddesappapi.service.CryptoCurrencyService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Transactional
@RequestMapping("/api/crypto")
@Tag(name = "Cryptos")
class CryptoCurrencyController {

    @Autowired lateinit var cryptoCurrencyService: CryptoCurrencyService
    @Operation(
        summary = "Get indicated crypto currency actual value",
        description = "Get the crypto currency actual value and update time")
    @GetMapping("/{symbol}")
    fun getCryptoCurrency(@PathVariable symbol: String): ResponseEntity<CryptoCurrency> {
        val cryptoCurrency = cryptoCurrencyService.getCryptoCurrency(symbol)
        return ResponseEntity.ok().body(cryptoCurrency)
    }
    @Operation(
        summary = "Get indicated crypto currency last 24hr values",
        description = "Get the crypto currency values for the last 24hr with volume, low and highs and the actual value")
    @GetMapping("/{symbol}/24hr")
    fun getCryptoCurrencyLast24(@PathVariable symbol: String): ResponseEntity<CryptoCurrency24hr> {
        val cryptoCurrency24 = cryptoCurrencyService.get24hrCurrencyQuoteFrom(symbol)
        return ResponseEntity.ok().body(cryptoCurrency24)
    }
    @Operation(
        summary = "Get all the actual values for the system defined crypto currencies",
        description = """
        Get a list of the actual values of the following crypto currencies:
        - ALICEUSDT
        - MATICUSDT
        - AXSUSDT
        - AAVEUSDT
        - ATOMUSDT
        - NEOUSDT
        - DOTUSDT
        - ETHUSDT
        - CAKEUSDT
        - BTCUSDT
        - BNBUSDT
        - ADAUSDT
        - TRXUSDT
        - AUDIOUSDT
    """
    )
    @GetMapping("/allQuotes")
    fun getAllQuotes(): ResponseEntity<CryptoCurrencyList> {
        val cryptoQuotes = cryptoCurrencyService.getAllCurrencyValues()
        return ResponseEntity.ok().body(cryptoQuotes)
    }
}