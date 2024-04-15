package ar.edu.unq.desapp.grupoI.backenddesappapi.service

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.CryptoCurrency
import org.springframework.stereotype.Service

interface CryptoCurrencyService {
    fun getCurrencyValue(symbol: String): CryptoCurrency?
    fun createCryptoCurrency(cryptoCurrency: CryptoCurrency): CryptoCurrency
}