package bado.ignacio.events.data

import java.lang.RuntimeException

class GetEventException(
    code: Int
) : RuntimeException("Error fetching events. HTTP response code: $code")