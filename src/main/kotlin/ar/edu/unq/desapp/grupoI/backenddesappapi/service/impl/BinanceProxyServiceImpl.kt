package ar.edu.unq.desapp.grupoI.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.CryptoCurrency
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.CryptoCurrency24hr
import ar.edu.unq.desapp.grupoI.backenddesappapi.service.BinanceProxyService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestTemplate

@Service
@Transactional
class BinanceProxyServiceImpl(): BinanceProxyService {

    val restTemplate: RestTemplate = RestTemplate()

    @Value("\${integration.binance.api.url:NONE}")
    lateinit var binanceApiURL: String

    override fun getCryptoCurrencyValue(symbol: String): CryptoCurrency? {
        val cryptoCurrency = restTemplate.getForObject("$binanceApiURL/ticker/price?symbol=$symbol", CryptoCurrency::class.java)
        return cryptoCurrency
    }

    override fun getCrypto24hrCurrencyValue(symbol: String): CryptoCurrency24hr? {
        return restTemplate.getForObject("$binanceApiURL/ticker/24hr?symbol=$symbol", CryptoCurrency24hr::class.java)
    }
}