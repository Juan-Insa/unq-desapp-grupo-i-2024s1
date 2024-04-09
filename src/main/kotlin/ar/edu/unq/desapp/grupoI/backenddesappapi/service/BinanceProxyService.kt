package ar.edu.unq.desapp.grupoI.backenddesappapi.service

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.CryptoCurrency
import org.springframework.stereotype.Service

@Service
interface BinanceProxyService {

    fun getCryptoCurrencyValue(symbol: String): CryptoCurrency?
}
