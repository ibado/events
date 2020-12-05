package bado.ignacio.events.domain

interface EventRepository {
    fun getMyEvents(): List<Event>
}