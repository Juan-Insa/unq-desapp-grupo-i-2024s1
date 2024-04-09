package ar.edu.unq.desapp.grupoI.backenddesappapi.model

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Currency
import jakarta.persistence.Entity
import jakarta.persistence.Id


import java.time.LocalDateTime

@Entity
class CryptoCurrency(
    @Id
    val symbol: Currency,
    var marketPrice: Float,
    var lastUpdateDateAndTime: String
) {
    //val currencyRate: Map<LocalDateTime, Long>? = null

    fun lastUpdateDateAndTime(lastUpdateDateAndTime: String) {
        this.lastUpdateDateAndTime = lastUpdateDateAndTime
    }
}
