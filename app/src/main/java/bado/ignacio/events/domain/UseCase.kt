package bado.ignacio.events.domain

interface UseCase<T, P> {
    fun invoke(params: P): T
}