package bado.ignacio.events.domain

interface EventRepository {
    fun getMyEvents(query: String, orderBy: OrderBy, page: Int): List<Event>

    enum class OrderBy {
        BY_NAME, BY_START_DATE
    }
}