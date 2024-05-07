package ar.edu.unq.desapp.grupoI.backenddesappapi.utils

import ar.edu.unq.desapp.grupoI.backenddesappapi.persistence.repository.CryptoCurrencyRepository
import ar.edu.unq.desapp.grupoI.backenddesappapi.persistence.repository.IntentionRepository
import ar.edu.unq.desapp.grupoI.backenddesappapi.persistence.repository.TransactionRepository
import ar.edu.unq.desapp.grupoI.backenddesappapi.persistence.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class DataServiceImpl: DataService {

    @Autowired lateinit var cryptoCurrencyRepository: CryptoCurrencyRepository
    @Autowired lateinit var intentionRepository: IntentionRepository
    @Autowired lateinit var transactionRepository: TransactionRepository
    @Autowired lateinit var userRepository: UserRepository

    override fun deleteAll() {
        cryptoCurrencyRepository.deleteAll()
        intentionRepository.deleteAll()
        transactionRepository.deleteAll()
        userRepository.deleteAll()
    }

}