package ar.edu.unq.desapp.grupoI.backenddesappapi.model

import jakarta.persistence.*

@Entity
@Table(name = "currency_rate")
class CurrencyRate(

    @ManyToOne(fetch = FetchType.LAZY)
    val cryptoCurrency: CryptoCurrency,
    var rate: Float,
    var dateTime: String
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
