package ar.edu.unq.desapp.grupoI.backenddesappapi.helpers

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

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

    fun getNewLocalDateTime(): LocalDateTime {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        return LocalDateTime.parse(this.getNewDateString(), formatter)
    }
}