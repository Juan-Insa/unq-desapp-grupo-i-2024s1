package ar.edu.unq.desapp.grupoI.backenddesappapi.model

import java.time.LocalDateTime

class CryptoAsset(
    val asset: String,
) {
    val date: LocalDateTime = LocalDateTime.now()
    val marketPrice: Long = 0
    val currencyRate: Map<LocalDateTime, Long>? = null
}