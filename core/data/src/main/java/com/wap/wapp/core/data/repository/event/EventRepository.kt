package com.wap.wapp.core.data.repository.event

import com.wap.wapp.core.model.event.Event
import java.time.LocalDate
import java.time.LocalDateTime

interface EventRepository {
    suspend fun getMonthEvents(date: LocalDate): Result<List<Event>>

    suspend fun getDateEvents(date: LocalDate): Result<List<Event>>

    suspend fun getEvent(date: LocalDateTime, eventId: String): Result<Event>

    suspend fun postEvent(
        title: String,
        content: String,
        location: String,
        startDateTime: LocalDateTime,
        endDateTime: LocalDateTime,
    ): Result<Unit>

    suspend fun updateEvent(
        eventId: String,
        title: String,
        content: String,
        location: String,
        startDateTime: LocalDateTime,
        endDateTime: LocalDateTime,
    ): Result<Unit>
}
