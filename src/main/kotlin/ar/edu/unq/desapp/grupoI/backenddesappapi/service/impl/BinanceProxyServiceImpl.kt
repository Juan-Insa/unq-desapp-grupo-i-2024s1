package ar.edu.unq.desapp.grupoI.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoI.backenddesappapi.exceptions.InvalidCryptoCurrencySymbol
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.CryptoCurrency
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.CryptoCurrency24hr
import ar.edu.unq.desapp.grupoI.backenddesappapi.service.BinanceProxyService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate

@Service
@Transactional
class BinanceProxyServiceImpl(): BinanceProxyService {

    val restTemplate: RestTemplate = RestTemplate()

    @Value("\${integration.binance.api.url:NONE}")
    lateinit var binanceApiURL: String

    override fun getCryptoCurrency(symbol: String): CryptoCurrency? {

        if (symbol.isBlank()) {
            throw IllegalArgumentException("The currency symbol must not be empty")
        }

        try {
            var cryptoCurrency = restTemplate.getForObject("${binanceApiURL}/ticker/price?symbol=${symbol}", CryptoCurrency::class.java)
            return cryptoCurrency

        } catch (e: HttpClientErrorException) {
            throw InvalidCryptoCurrencySymbol("The currency symbol provided is invalid")
        }


    }

    override fun getCrypto24hrCurrencyValue(symbol: String): CryptoCurrency24hr? {
        return restTemplate.getForObject("$binanceApiURL/ticker/24hr?symbol=$symbol", CryptoCurrency24hr::class.java)
    }
}