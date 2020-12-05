package bado.ignacio.events.ui.main

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bado.ignacio.events.domain.Event
import bado.ignacio.events.domain.GetMyEventsUseCase
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

    fun fetchEvents() {
        viewModelScope.launch {
            try {
                val eventList = withContext(Dispatchers.IO) {
                    getMyEventsUseCase.invoke()
                }
                Log.d(TAG, "response: $eventList")
                _events.value = eventList
            } catch (exception: Throwable) {
                Log.d(TAG, "error: ${exception.message}")
            }
        }
    }

    companion object {
        val TAG = MainViewModel::class.simpleName
    }
}