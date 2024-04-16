package ar.edu.unq.desapp.grupoI.backenddesappapi.service

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.CryptoCurrency
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.CryptoCurrency24hr
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.CryptoCurrencyList
import org.springframework.stereotype.Service

interface CryptoCurrencyService {
    fun getCurrencyValue(symbol: String): CryptoCurrency?
    fun createCryptoCurrency(cryptoCurrency: CryptoCurrency): CryptoCurrency
    fun getAllCurrencyValues(): CryptoCurrencyList
    fun get24hrCurrencyQuoteFrom(symbol: String): CryptoCurrency24hr?
}