package bado.ignacio.events.domain

import bado.ignacio.events.domain.EventRepository.*
import javax.inject.Inject

class GetMyEventsUseCase @Inject constructor(
    private val repository: EventRepository,
) : UseCase<Events, GetMyEventsUseCase.Params> {

    override fun invoke(params: Params) = repository.getMyEvents(
        params.query,
        params.orderBy,
        params.page,
    )

    class Params(
        val query: String,
        val orderBy: OrderBy,
        val page: Int,
    )
}