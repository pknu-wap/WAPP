package com.wap.wapp.core.network.source.event

import com.wap.wapp.core.network.model.event.EventResponse
import java.time.LocalDate

interface EventDataSource {
    suspend fun getMonthEventList(date: LocalDate): Result<List<EventResponse>>

    suspend fun getDateEventList(date: LocalDate): Result<List<EventResponse>>

    suspend fun getEventList(): Result<List<EventResponse>>

    suspend fun getEventListFromDate(date: LocalDate): Result<List<EventResponse>>

    suspend fun getEvent(eventId: String): Result<EventResponse>

    suspend fun postEvent(
        title: String,
        content: String,
        location: String,
        startDateTime: String,
        endDateTime: String,
    ): Result<Unit>

    suspend fun updateEvent(
        eventId: String,
        title: String,
        content: String,
        location: String,
        startDateTime: String,
        endDateTime: String,
    ): Result<Unit>
}
