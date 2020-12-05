package bado.ignacio.events

import org.threeten.bp.LocalDateTime

fun String.toDate(): LocalDateTime {
    return LocalDateTime.parse(this) // ToDo: parse to ISO_INSTANT
}