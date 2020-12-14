package bado.ignacio.events.domain

import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.Mockito.`when` as mockitoWhen
import org.mockito.MockitoAnnotations

class GetMyEventsUseCaseTest {

    private lateinit var useCase: GetMyEventsUseCase

    @Mock
    lateinit var repository: EventRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        useCase = GetMyEventsUseCase(repository)
    }

    @Test
    fun executeHappyPath() {

        val expected = EventRepository.Events(
            listOf(Event.empty()),
            true
        )

        val query = "asd"
        val order = EventRepository.OrderBy.BY_NAME
        val page = 1

        mockitoWhen(repository.getMyEvents(query, order, page)).thenReturn(expected)

        val actual = useCase.execute(GetMyEventsUseCase.Params(query, order, page))

        assertEquals(expected, actual)
        verify(repository, times(1)).getMyEvents(query, order, page)
    }
}