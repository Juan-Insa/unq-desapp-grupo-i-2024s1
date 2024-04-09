package ar.edu.unq.desapp.grupoI.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoI.backenddesappapi.controllers.dto.CryptoCurrencyDTO
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.CryptoCurrency
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.CurrentDateTime
import ar.edu.unq.desapp.grupoI.backenddesappapi.persistence.repository.CryptoCurrencyRepository
import ar.edu.unq.desapp.grupoI.backenddesappapi.service.BinanceProxyService
import ar.edu.unq.desapp.grupoI.backenddesappapi.service.CryptoCurrencyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import java.util.*

class CryptoCurrencyServiceImpl(): CryptoCurrencyService {

    @Autowired lateinit var cryptoCurrencyRepository: CryptoCurrencyRepository
    @Autowired lateinit var binanceProxyService: BinanceProxyService

    @Cacheable(value= ["cryptoCache"], key = "#symbol")
    override fun getCurrencyValue(symbol: String): CryptoCurrency? {
        val currencyValue = binanceProxyService.getCryptoCurrencyValue(symbol)
        val formatter = CurrentDateTime.getNewDateFormatter()

        if (currencyValue != null) {
            currencyValue.lastUpdateDateAndTime(formatter.format(Date()))
        }

        return currencyValue
    }


}