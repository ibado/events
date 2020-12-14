package bado.ignacio.events.data

import bado.ignacio.events.presentation.firstToUpper
import bado.ignacio.events.presentation.toDate

fun Event.toDomainEvent(): bado.ignacio.events.domain.Event {
    return bado.ignacio.events.domain.Event(
        name = this.name.text.firstToUpper(),
        startDate = this.start.local.toDate(),
        endDate = this.end.local.toDate(),
        description = this.description.text,
        isFree = this.isFree,
        currency = this.currency,
    )
}