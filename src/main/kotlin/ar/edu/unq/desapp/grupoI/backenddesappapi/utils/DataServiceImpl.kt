package ar.edu.unq.desapp.grupoI.backenddesappapi.utils

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.CryptoCurrency
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.Intention
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.Transaction
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Action
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Asset
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Operation
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.OperationState
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

    override fun createTestData() {
        deleteAll()

        val users = createUsers()
        val savedUsers = users.map { userRepository.save(it) }

        val intentions = createIntentions(savedUsers)
        val transactions = createTransactions(savedUsers, intentions)

        intentions.forEach { intentionRepository.save(it) }
        transactions.forEach { transactionRepository.save(it) }
        createCryptoCurrencies().forEach { cryptoCurrencyRepository.save(it) }
    }

    private fun createUsers(): List<User> {
        return listOf(
            User(
                name = "John",
                lastName = "Doe",
                email = "john.doe@example.com",
                address = "123 Main Street",
                password = "Password123!",
                cvu = "1234567890123456789012",
                cryptoWalletAddress = "12345678",
            ),
            User(
                name = "Jane",
                lastName = "Smith",
                email = "jane.smith@example.com",
                address = "456 Elm Street",
                password = "Password456!",
                cvu = "9876543210987654321098",
                cryptoWalletAddress = "87654321",
            ),
            User(
                name = "Alice",
                lastName = "Johnson",
                email = "alice.johnson@example.com",
                address = "789 Oak Street",
                password = "Password789!",
                cvu = "2468135790246813579024",
                cryptoWalletAddress = "11111111",
            ),
            User(
                name = "Bob",
                lastName = "Brown",
                email = "bob.brown@example.com",
                address = "1011 Pine Street",
                password = "Password1011!",
                cvu = "3692581470369258147036",
                cryptoWalletAddress = "22222222",
            ),
            User(
                name = "Eva",
                lastName = "Williams",
                email = "eva.williams@example.com",
                address = "1213 Cedar Street",
                password = "Password1213!",
                cvu = "9876543210987654321012",
                cryptoWalletAddress = "33333333",
            )
        )
    }

    private fun createIntentions(savedUsers: List<User>): List<Intention> {
        val intentions = mutableListOf(
            Intention(
                cryptoAsset = Asset.BTCUSDT,
                amount = 1.5,
                operation = Operation.BUY,
                price = 70995.59000000
            ),
            Intention(
                cryptoAsset = Asset.ETHUSDT,
                amount = 2.0,
                operation = Operation.SELL,
                price = 30000.0
            ),
            Intention(
                cryptoAsset = Asset.BTCUSDT,
                amount = 0.5,
                operation = Operation.BUY,
                price = 70995.59000000
            ),
            Intention(
                cryptoAsset = Asset.ETHUSDT,
                amount = 1.0,
                operation = Operation.SELL,
                price = 55000.0
            ),
            Intention(
                cryptoAsset = Asset.BTCUSDT,
                amount = 2.5,
                operation = Operation.BUY,
                price = 70995.59000000
            ),
            Intention(
                cryptoAsset = Asset.ALICEUSDT,
                amount = 2.5,
                operation = Operation.BUY,
                price = 1.26200000
            ),
            Intention(
                cryptoAsset = Asset.ALICEUSDT,
                amount = 1.0,
                operation = Operation.SELL,
                price = 1.26200000
            )
        )

        intentions.forEach {
            if (it.cryptoAsset == Asset.ALICEUSDT) {
                it.state = OperationState.INACTIVE
                it.user = savedUsers[0]
            }
        }

        savedUsers.forEachIndexed { index, user ->
            if (index < intentions.size) {
                intentions[index].user = user
            }
        }

        return intentions
    }

    private fun createTransactions(savedUsers: List<User>, intentions: List<Intention>): List<Transaction> {
        return listOf(
            Transaction(
                interestedUser = savedUsers[1],
                action = Action.TRANSFER
            ).apply { intention = intentions[1] },
            Transaction(
                interestedUser = savedUsers[2],
                action = Action.CONFIRMTRANSFER
            ).apply { intention = intentions[2] },
            Transaction(
                interestedUser = savedUsers[3],
                action = Action.CANCEL
            ).apply { intention = intentions[3] }
        )
    }

    private fun createCryptoCurrencies(): List<CryptoCurrency> {
        return listOf(
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
    }
    override fun deleteAll() {
        cryptoCurrencyRepository.deleteAll()
        transactionRepository.deleteAll()
        intentionRepository.deleteAll()
        userRepository.deleteAll()
    }

}