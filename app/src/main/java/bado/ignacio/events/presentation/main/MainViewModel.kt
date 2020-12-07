package bado.ignacio.events.presentation.main

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bado.ignacio.events.domain.Event
import bado.ignacio.events.domain.GetMyEventsUseCase
import bado.ignacio.events.domain.EventRepository.OrderBy
import bado.ignacio.events.presentation.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDateTime

class MainViewModel @ViewModelInject constructor(
    private val getMyEventsUseCase: GetMyEventsUseCase,
) : ViewModel() {

    private val _events: MutableLiveData<State<List<Event>>> = MutableLiveData()
    val events: LiveData<State<List<Event>>> = _events

    private var orderBy: OrderBy = OrderBy.BY_NAME
    private var query: String = ""

    var selectedEvent: Event = emptyEvent()

    init {
        fetchEvents()
    }

    private fun fetchEvents() {
        viewModelScope.launch {
            try {
                _events.value = State.Loading
                val eventList = withContext(Dispatchers.IO) {
                    getMyEventsUseCase.invoke(GetMyEventsUseCase.Params(query, orderBy))
                }
                Log.d(TAG, "response: $eventList")
                _events.value = State.Success(eventList)
            } catch (exception: Throwable) {
                Log.d(TAG, "error: ${exception.message}")
                _events.value = State.Error(exception)
            }
        }
    }

    fun orderByName() {
        orderBy = OrderBy.BY_NAME
        fetchEvents()
    }

    fun orderByDate() {
        orderBy = OrderBy.BY_START_DATE
        fetchEvents()
    }

    fun search(query: String) {
        this.query = query
        fetchEvents()
    }

    private fun emptyEvent() = Event(
        "name",
        LocalDateTime.now(),
        LocalDateTime.now(),
        null,
        false,
        "currency"
    )

    companion object {
        val TAG = MainViewModel::class.simpleName
    }
}