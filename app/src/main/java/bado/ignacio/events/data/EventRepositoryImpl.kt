package bado.ignacio.events.data

import bado.ignacio.events.domain.EventRepository
import bado.ignacio.events.domain.EventRepository.*
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val service: MyEventsService,
) : EventRepository {

    private var pageCount: Int = 1

    override fun getMyEvents(query: String, orderBy: OrderBy, page: Int): Events {
        val order = if (orderBy == OrderBy.BY_START_DATE) "start_asc" else "name_asc"

        if (pageCount < page) return Events(emptyList(), false)

        val call = service.fetchEvents(query, order, page)
        val response = call.execute()
        val body = response.body()
        pageCount = body?.pagination?.pageCount ?: 1
        val events = body?.events?.map {
            it.toDomainEvent()
        } ?: throw GetEventException(response.code())

        return Events(events, body.pagination.hasMoreItems)
    }
}