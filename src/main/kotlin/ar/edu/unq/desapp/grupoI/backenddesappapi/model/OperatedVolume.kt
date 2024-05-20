package ar.edu.unq.desapp.grupoI.backenddesappapi.model

class OperatedVolume (
    var requestDate: String,
    var totalAmountUSD: Double,
    var totalAmountPesos: Double,
    var assets: List<OperatedAsset>
)