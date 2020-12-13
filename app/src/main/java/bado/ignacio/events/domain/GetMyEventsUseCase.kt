package bado.ignacio.events.domain

import javax.inject.Inject

class GetMyEventsUseCase @Inject constructor(
    private val repository: EventRepository,
) : UseCase<List<Event>, GetMyEventsUseCase.Params> {

    override fun invoke(params: Params) = repository.getMyEvents(
        params.query,
        params.orderBy,
        params.page,
    )

    class Params(
        val query: String,
        val orderBy: EventRepository.OrderBy,
        val page: Int,
    )
}