package bado.ignacio.events.domain

interface UseCase<T, P> {
    fun execute(params: P): T
}