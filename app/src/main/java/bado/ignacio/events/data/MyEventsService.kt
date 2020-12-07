package bado.ignacio.events.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MyEventsService {

    @GET("users/me/events")
    fun fetchEvents(
        @Query("name_filter") nameFilter: String,
        @Query("order_by") orderBy: String,
    ): Call<MyEventsResponse>
}