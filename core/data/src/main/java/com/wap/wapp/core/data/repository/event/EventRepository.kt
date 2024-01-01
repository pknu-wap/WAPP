package com.wap.wapp.core.data.repository.event

import com.wap.wapp.core.model.event.Event
import java.time.LocalDate
import java.time.LocalDateTime

interface EventRepository {
    suspend fun getMonthEvents(date: LocalDate): Result<List<Event>>

    suspend fun postEvent(
        eventTitle: String,
        eventContent: String,
        eventLocation: String,
        eventStartDateTime: LocalDateTime,
        eventEndDateTime: LocalDateTime,
    ): Result<Unit>
}
