package bado.ignacio.events.data

import retrofit2.Call
import retrofit2.http.GET

interface MyEventsService {

    @GET("users/me/events")
    fun fetchEvents(): Call<MyEventsResponse>
}