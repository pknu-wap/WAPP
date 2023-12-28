package com.wap.wapp.core.data.repository.event

import com.wap.wapp.core.model.event.Event
import com.wap.wapp.core.network.source.event.EventDataSource
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val eventDataSource: EventDataSource,
) : EventRepository {
    override suspend fun getMonthEvents(date: LocalDate): Result<List<Event>> =
        eventDataSource.getMonthEvents(date).mapCatching { eventResponses ->
            eventResponses.map { eventResponse ->
                eventResponse.toDomain()
            }
        }

    override suspend fun postEvent(
        date: LocalDate,
        eventTitle: String,
        eventContent: String,
        eventLocation: String,
        eventDateTime: LocalDateTime,
    ): Result<Unit> =
        eventDataSource.postEvent(
            date = date,
            title = eventTitle,
            content = eventContent,
            location = eventLocation,
            dateTime = eventDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
        )
}
