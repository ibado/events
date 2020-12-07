package bado.ignacio.events.ui.main

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bado.ignacio.events.domain.Event
import bado.ignacio.events.domain.GetMyEventsUseCase
import bado.ignacio.events.domain.EventRepository.OrderBy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel @ViewModelInject constructor(
    private val getMyEventsUseCase: GetMyEventsUseCase,
) : ViewModel() {

    private val _events: MutableLiveData<List<Event>> = MutableLiveData()
    val events: LiveData<List<Event>> = _events

    init {
        fetchEvents()
    }

    private fun fetchEvents(orderBy: OrderBy = OrderBy.BY_NAME) {
        viewModelScope.launch {
            try {
                val eventList = withContext(Dispatchers.IO) {
                    getMyEventsUseCase.invoke(orderBy)
                }
                Log.d(TAG, "response: $eventList")
                _events.value = eventList
            } catch (exception: Throwable) {
                Log.d(TAG, "error: ${exception.message}")
            }
        }
    }

    fun orderByName() {
        fetchEvents(OrderBy.BY_NAME)
    }

    fun orderByDate() {
        fetchEvents(OrderBy.BY_START_DATE)
    }

    companion object {
        val TAG = MainViewModel::class.simpleName
    }
}