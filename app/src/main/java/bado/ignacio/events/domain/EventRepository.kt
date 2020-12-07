package bado.ignacio.events.domain

interface EventRepository {
    fun getMyEvents(orderBy: OrderBy): List<Event>

    enum class OrderBy {
        BY_NAME, BY_START_DATE
    }
}