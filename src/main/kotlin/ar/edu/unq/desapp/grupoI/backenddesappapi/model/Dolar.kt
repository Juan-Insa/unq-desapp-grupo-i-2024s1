package ar.edu.unq.desapp.grupoI.backenddesappapi.model

data class Dolar(
    val moneda: String,
    val casa: String,
    val nombre: String,
    val compra: Int,
    val venta: Int,
    val fechaActualizacion: String
)