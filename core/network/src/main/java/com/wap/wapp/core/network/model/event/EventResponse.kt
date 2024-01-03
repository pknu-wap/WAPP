package com.wap.wapp.core.network.model.event

import com.wap.wapp.core.model.event.Event
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class EventResponse(
    val content: String = "",
    val eventId: String = "",
    val location: String = "",
    val period: String = "",
    val title: String = "",
    val time: String = "",
) {
    private val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")

    fun toDomain() = Event(
        content = content,
        eventId = eventId,
        location = location,
        title = title,
        period = LocalDate.parse(this.period, formatter),
        time = time,
    )
}
