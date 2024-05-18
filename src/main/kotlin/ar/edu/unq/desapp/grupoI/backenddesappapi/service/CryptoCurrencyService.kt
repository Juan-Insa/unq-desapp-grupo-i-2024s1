package ar.edu.unq.desapp.grupoI.backenddesappapi.service

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.CryptoCurrency
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.CryptoCurrency24hr
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.CryptoCurrencyList

interface CryptoCurrencyService {
    fun getCurrencyValue(symbol: String): Float?
    fun getCryptoCurrency(symbol: String): CryptoCurrency?
    fun createCryptoCurrency(currency: CryptoCurrency): CryptoCurrency
    fun isValidPrice(symbol: String, price: Double): Boolean
    fun getAllCurrencyValues(): CryptoCurrencyList
    fun get24hrCurrencyQuoteFrom(symbol: String): CryptoCurrency24hr?
    fun validatePrice(symbol: String, price: Double)
}