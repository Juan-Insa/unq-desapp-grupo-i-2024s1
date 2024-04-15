package ar.edu.unq.desapp.grupoI.backenddesappapi.controllers

import ar.edu.unq.desapp.grupoI.backenddesappapi.controllers.dto.CryptoCurrencyDTO
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.CryptoCurrency
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

    @GetMapping("/crypto/{symbol}")
    fun getCryptoCurrencyValue(@PathVariable symbol: String): ResponseEntity<CryptoCurrency> {
        val cryptoCurrency = cryptoCurrencyService.getCurrencyValue(symbol)
        return ResponseEntity.ok().body(cryptoCurrency)
    }

}