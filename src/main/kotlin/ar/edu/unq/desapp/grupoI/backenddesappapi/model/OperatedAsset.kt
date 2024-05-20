package ar.edu.unq.desapp.grupoI.backenddesappapi.model

data class OperatedAsset(
    val cryptoAsset: String,
    var nominalAmount: Double,
    val currentQuote: Float,
    var quoteValueInARS: Double
)