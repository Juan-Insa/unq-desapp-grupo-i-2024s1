package ar.edu.unq.desapp.grupoI.backenddesappapi.model

class ActiveIntention (
    val creationDateTime: String,
    val cryptoAsset: CryptoCurrency,
    val nominalAmount: Double,
    val cryptoAssetQuote: Double,
    val operationAmountInPesos: Double,
    val user: User,
    val operationCount: Int,
    val reputation: String) {
}