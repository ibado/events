package bado.ignacio.events.domain

import javax.inject.Inject

class GetMyEventsUseCase @Inject constructor(
    private val repository: EventRepository,
) : UseCase<List<Event>, EventRepository.OrderBy> {

    override fun invoke(params: EventRepository.OrderBy) = repository.getMyEvents(params)
}