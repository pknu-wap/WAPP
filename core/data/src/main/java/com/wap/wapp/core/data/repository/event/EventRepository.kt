package com.wap.wapp.core.data.repository.event

import com.wap.wapp.core.model.event.Event
import java.time.LocalDate

interface EventRepository {
    suspend fun getMonthEvents(date: LocalDate): Result<List<Event>>

    suspend fun getEvent(date: LocalDate, eventId: String): Result<Event>

    suspend fun postEvent(
        date: LocalDate,
        eventTitle: String,
        eventContent: String,
        eventLocation: String,
        eventDate: String,
        eventTime: String,
    ): Result<Unit>
}
