package bado.ignacio.events.data

import com.squareup.moshi.Json

data class MyEventsResponse(
    val pagination: Pagination,
    val events: List<Event>,
)

data class Pagination(
    @field:Json(name = "object_count") val objectCount: Int,
    @field:Json(name = "page_number") val pageNumber: Int,
    @field:Json(name = "page_size") val pageSize: Int,
    @field:Json(name = "page_count") val pageCount: Int,
    @field:Json(name = "has_more_items") val hasMoreItems: Boolean,
)

data class Event(
    val name: Name,
    val description: Description,
    val start: DateInfo,
    val end: DateInfo,
    val currency: String,
    @field:Json(name = "is_free") val isFree: Boolean,
)

data class Name(val text: String)
data class Description(val text: String?)
data class DateInfo(
    val timezone: String,
    val local: String,
    val utc: String,
)