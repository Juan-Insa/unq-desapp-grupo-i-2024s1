package ar.edu.unq.desapp.grupoI.backenddesappapi.helpers

import java.text.SimpleDateFormat
import java.util.Date

object CurrentDateTime {

    private const val DATE_FORMAT = "dd/MM/yyyy HH:mm:ss"

    fun getNewDate(): Date {
        return Date()
    }

    fun getNewDateString(): String {
        val formatter = SimpleDateFormat(DATE_FORMAT)
        return formatter.format(getNewDate())
    }

    fun getNewDateFormatter(): SimpleDateFormat {
        return SimpleDateFormat(DATE_FORMAT)
    }
}