package ar.edu.unq.desapp.grupoI.backenddesappapi.model

import jakarta.persistence.*

@Entity
@Table(name = "crypto_currency")
class CryptoCurrency(
    val symbol: String,
    var marketPrice: Float,
    var lastUpdateDateAndTime: String
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @OneToMany(mappedBy = "crypto_currency", cascade = [CascadeType.ALL])
    val currencyRates: MutableList<CurrencyRate> = mutableListOf()

    fun lastUpdateDateAndTime(lastUpdateDateAndTime: String) {
        this.lastUpdateDateAndTime = lastUpdateDateAndTime
    }
}
