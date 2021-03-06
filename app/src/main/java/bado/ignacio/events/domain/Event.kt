package bado.ignacio.events.domain

import org.threeten.bp.LocalDateTime

data class Event(
    val name: String,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val description: String?,
    val isFree: Boolean,
    val currency: String,
) {
    companion object {
        fun empty() = Event(
            "name",
            LocalDateTime.now(),
            LocalDateTime.now(),
            null,
            false,
            "currency"
        )
    }
}