package ar.edu.unq.desapp.grupoI.backenddesappapi.utils

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.CryptoCurrency
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.Intention
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Asset
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Operation
import ar.edu.unq.desapp.grupoI.backenddesappapi.persistence.repository.CryptoCurrencyRepository
import ar.edu.unq.desapp.grupoI.backenddesappapi.persistence.repository.IntentionRepository
import ar.edu.unq.desapp.grupoI.backenddesappapi.persistence.repository.TransactionRepository
import ar.edu.unq.desapp.grupoI.backenddesappapi.persistence.repository.UserRepository
import ar.edu.unq.desapp.grupoI.backenddesappapi.service.IntentionService
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

    override fun createTestData() {
        deleteAll()

        var users = mutableListOf<User>().apply {
            add(User(
                name = "John",
                lastName = "Doe",
                email = "john.doe@example.com",
                address = "123 Main Street",
                password = "Password123!",
                cvu = "1234567890123456789012",
                cryptoWalletAddress = "12345678",
                reputation = 80
            ))

            add(User(
                name = "Jane",
                lastName = "Smith",
                email = "jane.smith@example.com",
                address = "456 Elm Street",
                password = "Password456!",
                cvu = "9876543210987654321098",
                cryptoWalletAddress = "87654321",
                reputation = 90
            ))

            add(User(
                name = "Alice",
                lastName = "Johnson",
                email = "alice.johnson@example.com",
                address = "789 Oak Street",
                password = "Password789!",
                cvu = "2468135790246813579024",
                cryptoWalletAddress = "11111111",
                reputation = 85
            ))

            add(User(
                name = "Bob",
                lastName = "Brown",
                email = "bob.brown@example.com",
                address = "1011 Pine Street",
                password = "Password1011!",
                cvu = "3692581470369258147036",
                cryptoWalletAddress = "22222222",
                reputation = 95
            ))

            add(User(
                name = "Eva",
                lastName = "Williams",
                email = "eva.williams@example.com",
                address = "1213 Cedar Street",
                password = "Password1213!",
                cvu = "9876543210987654321012",
                cryptoWalletAddress = "33333333",
                reputation = 75
            ))
        }

        val savedUsers = users.map { userRepository.save(it) }

        val intentions = listOf(
            Intention(
                cryptoAsset = Asset.BTCUSDT,
                amount = 1.5,
                operation = Operation.BUY,
                //priceInPesos = 75000.0,
                price = 50000.0
            ),
            Intention(
                cryptoAsset = Asset.ETHUSDT,
                amount = 2.0,
                operation = Operation.SELL,
                //priceInPesos = 60000.0,
                price = 30000.0
            ),
            Intention(
                cryptoAsset = Asset.BTCUSDT,
                amount = 0.5,
                operation = Operation.BUY,
                //priceInPesos = 35000.0,
                price = 70000.0
            ),
            Intention(
                cryptoAsset = Asset.ETHUSDT,
                amount = 1.0,
                operation = Operation.SELL,
                //priceInPesos = 55000.0,
                price = 55000.0
            ),
            Intention(
                cryptoAsset = Asset.BTCUSDT,
                amount = 2.5,
                operation = Operation.BUY,
                //priceInPesos = 80000.0,
                price = 40000.0
            )
        )

        intentions.forEachIndexed { index, intention ->
            intention.user = savedUsers[index]
            intentionRepository.save(intention)
        }

        val cryptoCurrencies = listOf(
            CryptoCurrency(symbol = "ALICEUSDT", marketPrice = 10.0f),
            CryptoCurrency(symbol = "MATICUSDT", marketPrice = 5.0f),
            CryptoCurrency(symbol = "AXSUSDT", marketPrice = 15.0f),
            CryptoCurrency(symbol = "AAVEUSDT", marketPrice = 20.0f),
            CryptoCurrency(symbol = "ATOMUSDT", marketPrice = 25.0f),
            CryptoCurrency(symbol = "NEOUSDT", marketPrice = 30.0f),
            CryptoCurrency(symbol = "DOTUSDT", marketPrice = 35.0f),
            CryptoCurrency(symbol = "ETHUSDT", marketPrice = 3000.0f),
            CryptoCurrency(symbol = "CAKEUSDT", marketPrice = 50.0f),
            CryptoCurrency(symbol = "BTCUSDT", marketPrice = 60000.0f),
            CryptoCurrency(symbol = "BNBUSDT", marketPrice = 400.0f),
            CryptoCurrency(symbol = "ADAUSDT", marketPrice = 2.0f),
            CryptoCurrency(symbol = "TRXUSDT", marketPrice = 0.1f),
            CryptoCurrency(symbol = "AUDIOUSDT", marketPrice = 3.0f)
        )

        cryptoCurrencies.forEach { cryptoCurrencyRepository.save(it) }
    }
    override fun deleteAll() {
        cryptoCurrencyRepository.deleteAll()
        transactionRepository.deleteAll()
        intentionRepository.deleteAll()
        userRepository.deleteAll()
    }



}