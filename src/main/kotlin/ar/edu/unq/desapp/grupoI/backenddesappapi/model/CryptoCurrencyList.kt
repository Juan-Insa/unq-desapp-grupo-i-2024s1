package ar.edu.unq.desapp.grupoI.backenddesappapi.model
import java.io.Serializable
class CryptoCurrencyList: Serializable {
    val cryptos: MutableList<CryptoCurrency> = mutableListOf()

    fun addCrypto(cripto: CryptoCurrency) {
        cryptos.add(cripto)
    }

    fun getList():  MutableList<CryptoCurrency>{
        return cryptos
    }
}