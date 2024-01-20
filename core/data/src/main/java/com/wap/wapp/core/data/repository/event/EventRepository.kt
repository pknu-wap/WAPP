package com.wap.wapp.core.data.repository.event

import com.wap.wapp.core.model.event.Event
import java.time.LocalDate
import java.time.LocalDateTime

interface EventRepository {
    suspend fun getMonthEventList(date: LocalDate): Result<List<Event>>

    suspend fun getDateEventList(date: LocalDate): Result<List<Event>>

    suspend fun getEventListFromDate(date: LocalDate): Result<List<Event>>

    suspend fun getEventList(): Result<List<Event>>

    suspend fun getEvent(eventId: String): Result<Event>

    suspend fun deleteEvent(eventId: String): Result<Unit>

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
