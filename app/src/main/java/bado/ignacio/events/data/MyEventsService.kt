package bado.ignacio.events.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MyEventsService {

    @GET("users/me/events")
    fun fetchEvents(@Query("order_by") orderBy: String = "start_asc"): Call<MyEventsResponse>
}