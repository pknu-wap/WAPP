package com.wap.wapp.core.data.repository.event

import com.wap.wapp.core.model.event.Event
import com.wap.wapp.core.network.model.event.EventRequest
import com.wap.wapp.core.network.source.event.EventDataSource
import java.time.LocalDate
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

    override suspend fun getEvent(date: LocalDate, eventId: String): Result<Event> =
        eventDataSource.getEvent(date, eventId).mapCatching { eventResponse ->
            eventResponse.toDomain()
        }

    override suspend fun postEvent(
        date: LocalDate,
        eventTitle: String,
        eventContent: String,
        eventLocation: String,
        eventDate: String,
        eventTime: String,
    ): Result<Unit> =
        eventDataSource.postEvent(
            date = date,
            EventRequest(
                title = eventTitle,
                content = eventContent,
                location = eventLocation,
                period = eventDate,
                time = eventTime,
            ),
        )
}
