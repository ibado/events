package bado.ignacio.events.domain

interface EventRepository {
    fun getMyEvents(query: String, orderBy: OrderBy, page: Int): Events

    enum class OrderBy {
        BY_NAME, BY_START_DATE
    }

    data class Events(val events: List<Event>, val morePages: Boolean)
}