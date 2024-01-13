package com.wap.wapp.core.data.repository.event

import com.wap.wapp.core.data.utils.toISOLocalDateTimeString
import com.wap.wapp.core.model.event.Event
import com.wap.wapp.core.network.source.event.EventDataSource
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val eventDataSource: EventDataSource,
) : EventRepository {
    override suspend fun getMonthEventList(date: LocalDate): Result<List<Event>> =
        eventDataSource.getMonthEventList(date).mapCatching { eventResponses ->
            eventResponses.map { eventResponse ->
                eventResponse.toDomain()
            }
        }

    override suspend fun getDateEventList(date: LocalDate): Result<List<Event>> =
        eventDataSource.getDateEventList(date).mapCatching { eventResponses ->
            eventResponses.map { eventResponse ->
                eventResponse.toDomain()
            }
        }

    override suspend fun getEvent(date: LocalDateTime, eventId: String): Result<Event> =
        eventDataSource.getEvent(date, eventId).mapCatching { eventResponse ->
            eventResponse.toDomain()
        }

    override suspend fun postEvent(
        title: String,
        content: String,
        location: String,
        startDateTime: LocalDateTime,
        endDateTime: LocalDateTime,
    ): Result<Unit> =
        eventDataSource.postEvent(
            title = title,
            content = content,
            location = location,
            startDateTime = startDateTime.toISOLocalDateTimeString(),
            endDateTime = endDateTime.toISOLocalDateTimeString(),
        )

    override suspend fun updateEvent(
        eventId: String,
        title: String,
        content: String,
        location: String,
        startDateTime: LocalDateTime,
        endDateTime: LocalDateTime,
    ): Result<Unit> =
        eventDataSource.updateEvent(
            eventId = eventId,
            title = title,
            content = content,
            location = location,
            startDateTime = startDateTime.toISOLocalDateTimeString(),
            endDateTime = endDateTime.toISOLocalDateTimeString(),
        )
}
