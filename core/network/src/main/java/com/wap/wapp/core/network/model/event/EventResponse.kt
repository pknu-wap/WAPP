package com.wap.wapp.core.network.model.event

import com.wap.wapp.core.model.event.Event
import com.wap.wapp.core.network.utils.toISOLocalDateTime
import java.time.format.DateTimeFormatter

data class EventResponse(
    val content: String = "",
    val eventId: String = "",
    val location: String = "",
    val title: String = "",
    val startDateTime: String = "",
    val endDateTime: String = "",
) {
    private val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")

    fun toDomain() = Event(
        content = content,
        eventId = eventId,
        location = location,
        title = title,
        startDateTime = startDateTime.toISOLocalDateTime(),
        endDateTime = endDateTime.toISOLocalDateTime(),
    )
}
