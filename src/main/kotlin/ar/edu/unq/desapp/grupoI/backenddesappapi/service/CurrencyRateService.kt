package ar.edu.unq.desapp.grupoI.backenddesappapi.service

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.CurrencyRate

interface CurrencyRateService {
    fun createCurrencyRate(currencyRate: CurrencyRate): CurrencyRate
}