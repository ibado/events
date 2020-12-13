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

class MainViewModel @ViewModelInject constructor(
    private val getMyEventsUseCase: GetMyEventsUseCase,
) : ViewModel() {

    private val _events: MutableLiveData<State<List<Event>>> = MutableLiveData()
    val events: LiveData<State<List<Event>>> = _events

    var selectedEvent: Event = Event.empty()
    var loadMore = true

    private var orderBy: OrderBy = OrderBy.BY_NAME
    private var query: String = ""
    private var page = 1
    private var current: List<Event>? = null

    init {
        _events.value = State.Loading
        fetchEvents()
    }

    fun sortByName() {
        orderBy = OrderBy.BY_NAME
        sort(compareBy { it.name })
    }

    fun sortByDate() {
        orderBy = OrderBy.BY_START_DATE
        sort(compareBy { it.startDate })
    }

    fun search(query: String) {
        page = 1
        this.query = query
        _events.value = State.Loading
        fetchEvents()
    }

    fun loadMore() {
        if (loadMore) {
            page++
            fetchEvents()
            loadMore = false
        }
    }

    private fun sort(comparator: Comparator<Event>) {
        _events.value = State.Loading
        current?.sortedWith(comparator)?.apply {
            _events.value = State.Success(this)
        }
    }

    private fun fetchEvents() {
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    getMyEventsUseCase.invoke(GetMyEventsUseCase.Params(query, orderBy, page))
                }
                loadMore = result.morePages
                val items = result.events

                current = if (page > 1) current?.plus(items) else items

                _events.value = State.Success(items)
            } catch (exception: Throwable) {
                Log.d(TAG, "error: ${exception.message}")
                _events.value = State.Error(exception)
            }
        }
    }

    companion object {
        val TAG = MainViewModel::class.simpleName
    }
}