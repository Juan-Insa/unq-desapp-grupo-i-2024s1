package ar.edu.unq.desapp.grupoI.backenddesappapi.model

import jakarta.persistence.*

@Entity
@Table(name = "currency_rate")
class CurrencyRate(

    @ManyToOne(fetch = FetchType.LAZY)
    val cryptoCurrency: CryptoCurrency,
    var dateTime: String,
    var rate: Float
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
