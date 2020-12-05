package bado.ignacio.events.domain

import javax.inject.Inject

class GetMyEventsUseCase @Inject constructor(
    private val repository: EventRepository,
) : UseCase<List<Event>> {

    override fun invoke() = repository.getMyEvents()
}