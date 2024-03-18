package com.wap.wapp.core.network.model.event

import com.wap.wapp.core.model.event.Event
import com.wap.wapp.core.network.utils.toISOLocalDateTime

data class EventResponse(
    val content: String = "",
    val eventId: String = "",
    val location: String = "",
    val title: String = "",
    val startDateTime: String = "",
    val endDateTime: String = "",
) {
    fun toDomain() = Event(
        content = content,
        eventId = eventId,
        location = location,
        title = title,
        startDateTime = startDateTime.toISOLocalDateTime(),
        endDateTime = endDateTime.toISOLocalDateTime(),
    )
}
