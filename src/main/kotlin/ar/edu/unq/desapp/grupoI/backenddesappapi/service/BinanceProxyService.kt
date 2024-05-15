package ar.edu.unq.desapp.grupoI.backenddesappapi.service

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.CryptoCurrency
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.CryptoCurrency24hr
import org.springframework.stereotype.Service

interface BinanceProxyService {

    fun getCryptoCurrency(symbol: String): CryptoCurrency?
    fun getCrypto24hrCurrencyValue(symbol: String): CryptoCurrency24hr?
}
