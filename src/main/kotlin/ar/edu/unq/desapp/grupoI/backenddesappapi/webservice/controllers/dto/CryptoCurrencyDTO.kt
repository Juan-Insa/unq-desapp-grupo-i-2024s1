package ar.edu.unq.desapp.grupoI.backenddesappapi.webservice.controllers.dto

import java.time.LocalDateTime

class CryptoCurrencyDTO(
    val symbol: String,
    val price: Float,
    val lastUpdateDateAndTime: LocalDateTime,
) {

}
