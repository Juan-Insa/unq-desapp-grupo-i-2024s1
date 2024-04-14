package ar.edu.unq.desapp.grupoI.backenddesappapi.model

class CryptoCurrencyList {
    val cryptos: MutableList<CryptoCurrency> = mutableListOf()

    fun addCrypto(cripto: CryptoCurrency) {
        cryptos.add(cripto)
    }
}