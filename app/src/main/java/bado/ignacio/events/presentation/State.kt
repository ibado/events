package bado.ignacio.events.presentation

sealed class State<out T> {
    data class Success<T>(val value: T) : State<T>()
    data class Error(val error: Throwable) : State<Nothing>()
    object Loading : State<Nothing>()
}
