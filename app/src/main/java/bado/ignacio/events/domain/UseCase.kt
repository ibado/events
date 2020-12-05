package bado.ignacio.events.domain

interface UseCase<T> {
    fun invoke(): T
}