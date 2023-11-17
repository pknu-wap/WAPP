package com.wap.wapp.core.network.model.event

import com.wap.wapp.core.model.event.Event
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class EventResponse(
    val content: String,
    val eventId: Int,
    val location: String,
    val period: String,
    val title: String,
) {
    fun toDomain() = Event(
        content = content,
        eventId = eventId,
        location = location,
        id = title,
        period = LocalDateTime.parse(
            this.period,
            DateTimeFormatter.ISO_LOCAL_DATE_TIME,
        ),
    )
}
