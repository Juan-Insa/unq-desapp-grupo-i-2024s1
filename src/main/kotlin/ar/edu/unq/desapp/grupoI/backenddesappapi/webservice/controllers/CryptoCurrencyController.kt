package ar.edu.unq.desapp.grupoI.backenddesappapi.webservice.controllers

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.CryptoCurrency
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.CryptoCurrency24hr
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.CryptoCurrencyList
import ar.edu.unq.desapp.grupoI.backenddesappapi.service.CryptoCurrencyService
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
class CryptoCurrencyController {

    @Autowired lateinit var cryptoCurrencyService: CryptoCurrencyService

    @GetMapping("/{symbol}")
    fun getCryptoCurrency(@PathVariable symbol: String): ResponseEntity<CryptoCurrency> {
        val cryptoCurrency = cryptoCurrencyService.getCryptoCurrency(symbol)
        return ResponseEntity.ok().body(cryptoCurrency)
    }
    @GetMapping("/{symbol}/24hr")
    fun getCryptoCurrencyLast24(@PathVariable symbol: String): ResponseEntity<CryptoCurrency24hr> {
        val cryptoCurrency24 = cryptoCurrencyService.get24hrCurrencyQuoteFrom(symbol)
        return ResponseEntity.ok().body(cryptoCurrency24)
    }
    @GetMapping("/allQuotes")
    fun getAllQuotes(): ResponseEntity<CryptoCurrencyList> {
        val cryptoQuotes = cryptoCurrencyService.getAllCurrencyValues()
        return ResponseEntity.ok().body(cryptoQuotes)
    }
}