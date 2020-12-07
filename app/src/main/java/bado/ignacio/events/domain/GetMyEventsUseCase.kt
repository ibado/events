package bado.ignacio.events.domain

import javax.inject.Inject

class GetMyEventsUseCase @Inject constructor(
    private val repository: EventRepository,
) : UseCase<List<Event>, GetMyEventsUseCase.Params> {

    override fun invoke(params: Params) = repository.getMyEvents(params.query, params.orderBy)

    class Params(val query: String, val orderBy: EventRepository.OrderBy)
}