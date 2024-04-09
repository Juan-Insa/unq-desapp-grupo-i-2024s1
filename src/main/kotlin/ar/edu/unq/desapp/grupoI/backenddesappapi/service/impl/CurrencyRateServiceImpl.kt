package ar.edu.unq.desapp.grupoI.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoI.backenddesappapi.persistence.repository.CurrencyRateRepository
import ar.edu.unq.desapp.grupoI.backenddesappapi.service.CurrencyRateService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CurrencyRateServiceImpl: CurrencyRateService{

    @Autowired lateinit var currencyRateRepository: CurrencyRateRepository
}