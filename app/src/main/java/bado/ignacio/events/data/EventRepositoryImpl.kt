package bado.ignacio.events.data

import bado.ignacio.events.domain.Event
import bado.ignacio.events.domain.EventRepository
import bado.ignacio.events.domain.EventRepository.*
import bado.ignacio.events.toDate
import java.lang.RuntimeException
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val service: MyEventsService,
) : EventRepository {

    override fun getMyEvents(query: String, orderBy: OrderBy): List<Event> {
        val order = if (orderBy == OrderBy.BY_START_DATE) "start_asc" else "name_asc"
        val call = service.fetchEvents(query, order)
        return call.execute().body()?.events?.map {
            Event(
                name = it.name.text,
                startDate = it.start.local.toDate(),
                endDate = it.end.local.toDate(),
                description = it.description.text,
                isFree = it.isFree,
                currency = it.currency,
            )
        } ?: throw RuntimeException("Error fetching events")
    }
}