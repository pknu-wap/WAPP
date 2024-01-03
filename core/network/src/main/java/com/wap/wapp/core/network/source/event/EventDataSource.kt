package com.wap.wapp.core.network.source.event

import com.wap.wapp.core.network.model.event.EventResponse
import java.time.LocalDate
import java.time.LocalDateTime

interface EventDataSource {
    suspend fun getMonthEvents(date: LocalDate): Result<List<EventResponse>>

    suspend fun getEvent(date: LocalDateTime, eventId: String): Result<EventResponse>

    suspend fun postEvent(
        title: String,
        content: String,
        location: String,
        startDateTime: String,
        endDateTime: String,
    ): Result<Unit>
}
