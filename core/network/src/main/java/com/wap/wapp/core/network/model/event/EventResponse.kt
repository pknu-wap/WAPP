package com.wap.wapp.core.network.model.event

import com.google.firebase.firestore.PropertyName
import com.wap.wapp.core.model.event.Event
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class EventResponse(
    val content: String = "",
    @PropertyName("event_id") val eventId: Int = 0,
    val location: String = "",
    val period: String = "",
    val title: String = "",
) {
    private val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")

    fun toDomain() = Event(
        content = content,
        eventId = eventId,
        location = location,
        id = title,
        period = LocalDate.parse(this.period, formatter),
    )
}
