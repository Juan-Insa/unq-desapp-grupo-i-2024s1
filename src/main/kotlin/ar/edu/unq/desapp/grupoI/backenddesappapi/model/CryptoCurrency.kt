package ar.edu.unq.desapp.grupoI.backenddesappapi.model


import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*
import java.io.Serializable
@Entity
@Table(name = "crypto_currency")
class CryptoCurrency(
    @JsonProperty("symbol")
    val symbol: String,
    @JsonProperty("price")
    var marketPrice: Float,
    var lastUpdateDateAndTime: String? = null
): Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun lastUpdateDateAndTime(lastUpdateDateAndTime: String) {
        this.lastUpdateDateAndTime = lastUpdateDateAndTime
    }
}
