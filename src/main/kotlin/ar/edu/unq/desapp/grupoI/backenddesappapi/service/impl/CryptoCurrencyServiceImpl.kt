package ar.edu.unq.desapp.grupoI.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.CryptoCurrency
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.CryptoCurrency24hr
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.CryptoCurrencyList
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Asset
import ar.edu.unq.desapp.grupoI.backenddesappapi.helpers.CurrentDateTime
import ar.edu.unq.desapp.grupoI.backenddesappapi.persistence.repository.CryptoCurrencyRepository
import ar.edu.unq.desapp.grupoI.backenddesappapi.service.BinanceProxyService
import ar.edu.unq.desapp.grupoI.backenddesappapi.service.CryptoCurrencyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class CryptoCurrencyServiceImpl(): CryptoCurrencyService {

    @Autowired lateinit var cryptoCurrencyRepository: CryptoCurrencyRepository
    @Autowired lateinit var binanceProxyService: BinanceProxyService
    override fun getCurrencyValue(symbol: String): Float? {
        TODO("Not yet implemented")
    }

    override fun getCryptoCurrency(symbol: String): CryptoCurrency? {
        val currencyValue = binanceProxyService.getCryptoCurrency(symbol)
        val formatter = CurrentDateTime.getNewDateFormatter()

        if (currencyValue != null) {
            currencyValue.lastUpdateDateAndTime(formatter.format(Date()))
        }

        return currencyValue
    }

    override fun isValidPrice(symbol: String, price: Double): Boolean {
        val currencyValue = getCryptoCurrency(symbol)!!.marketPrice

        val fivePercentDiff = currencyValue * 0.05

        val lowerLimit = currencyValue - fivePercentDiff
        val upperLimit = currencyValue + fivePercentDiff

        return price in lowerLimit..upperLimit
    }


    override fun createCryptoCurrency(cryptoCurrency: CryptoCurrency): CryptoCurrency {
        return cryptoCurrencyRepository.save(cryptoCurrency)
    }

    override fun getAllCurrencyValues(): CryptoCurrencyList {
        val cryptos = CryptoCurrencyList()
        for(crypto in Asset.values()) {
            val formatter = CurrentDateTime.getNewDateFormatter()
            val cripto = binanceProxyService.getCryptoCurrency(crypto.name)
            if (cripto != null) {
                cripto.lastUpdateDateAndTime(formatter.format(Date()))
                cryptos.addCrypto(cripto)
            }
        }
        return cryptos
    }
    override fun get24hrCurrencyQuoteFrom(symbol: String): CryptoCurrency24hr? {
        return binanceProxyService.getCrypto24hrCurrencyValue(symbol)
    }
}