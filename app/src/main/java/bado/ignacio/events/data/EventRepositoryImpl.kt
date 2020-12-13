package bado.ignacio.events.data

import bado.ignacio.events.domain.Event
import bado.ignacio.events.domain.EventRepository
import bado.ignacio.events.domain.EventRepository.*
import bado.ignacio.events.presentation.firstToUpper
import bado.ignacio.events.presentation.toDate
import java.lang.RuntimeException
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val service: MyEventsService,
) : EventRepository {

    private var pageCount: Int? = null

    override fun getMyEvents(query: String, orderBy: OrderBy, page: Int): Events {
        val order = if (orderBy == OrderBy.BY_START_DATE) "start_asc" else "name_asc"

        pageCount?.let {
            if (it < page) return Events(emptyList(), false)
        }

        val call = service.fetchEvents(query, order, page).execute()
        val response = call.body()
        pageCount = response?.pagination?.pageCount
        val events = response?.events?.map {
            Event(
                name = it.name.text.firstToUpper(),
                startDate = it.start.local.toDate(),
                endDate = it.end.local.toDate(),
                description = it.description.text,
                isFree = it.isFree,
                currency = it.currency,
            )
        } ?: throw RuntimeException("Error fetching events. HTTP error: ${call.code()}")

        return Events(events, response.pagination.hasMoreItems)
    }
}