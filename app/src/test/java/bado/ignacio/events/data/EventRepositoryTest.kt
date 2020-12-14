package bado.ignacio.events.data

import bado.ignacio.events.domain.EventRepository
import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.`when` as mockitoWhen
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime

class EventRepositoryTest {

    @Mock
    lateinit var service: MyEventsService

    private lateinit var repository: EventRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = EventRepositoryImpl(service)
    }

    @After
    fun tearDown() {
        Mockito.clearInvocations(service)
    }

    @Test
    fun getMyEventsNoMorePages() {
        val eventsResponse = getMockedEventResponse()

        val query = "asd"
        mockitoWhen(service.fetchEvents(query, "start_asc", 1))
            .thenReturn(MockCall(Response.success(eventsResponse)))

        val actual = repository.getMyEvents(query, EventRepository.OrderBy.BY_NAME, 2)
        val expected = EventRepository.Events(emptyList(), false)

        assertEquals(expected, actual)
    }

    @Test
    fun getMyEventsHappyPath() {
        val eventsResponse = getMockedEventResponse()
        val query = "asd"

        mockitoWhen(service.fetchEvents(query, "name_asc", 1))
            .thenReturn(MockCall(Response.success(eventsResponse)))

        val actual = repository.getMyEvents(query, EventRepository.OrderBy.BY_NAME, 1)
        val expected = EventRepository.Events(
            eventsResponse.events.map { it.toDomainEvent() },
            eventsResponse.pagination.hasMoreItems
        )

        assertEquals(expected, actual)
    }

    @Test(expected = GetEventException::class)
    fun getMyEventsFails() {
        val query = "asd"

        mockitoWhen(service.fetchEvents(query, "name_asc", 1))
            .thenReturn(MockCall(
                Response.error(
                    500,
                    ResponseBody.create(null, "")
                ))
            )

        repository.getMyEvents(query, EventRepository.OrderBy.BY_NAME, 1)
    }

    private fun getMockedEventResponse(): MyEventsResponse {
        val pagination = Pagination(20, 1, 20, 1, false)
        val events = listOf(
            Event(
                Name("name"),
                Description(null),
                DateInfo("", LocalDateTime.now().toString(), ""),
                DateInfo("", LocalDateTime.now().toString(), ""),
                "usd",
                false
            )
        )
        return MyEventsResponse(pagination, events)
    }

    class MockCall(private val response: Response<MyEventsResponse>) : Call<MyEventsResponse> {

        override fun execute(): Response<MyEventsResponse> {
            return response
        }

        override fun clone(): Call<MyEventsResponse> = this.clone()

        override fun enqueue(callback: Callback<MyEventsResponse>) {}

        override fun isExecuted(): Boolean = false

        override fun cancel() {}

        override fun isCanceled(): Boolean = false

        override fun request(): Request = Mockito.mock(Request::class.java)

        override fun timeout(): Timeout = Mockito.mock(Timeout::class.java)

    }
}