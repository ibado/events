package bado.ignacio.events

import org.threeten.bp.LocalDateTime
import java.util.Locale

fun String.toDate(): LocalDateTime {
    return LocalDateTime.parse(this) // ToDo: parse to ISO_INSTANT
}

fun LocalDateTime.pretty(): String {
    val month = month.toString().toLowerCase(Locale.getDefault())
    val monthPretty = Character.toUpperCase(month[0]) + month.substring(1)
    return "$monthPretty $dayOfMonth, $year"
}