package com.wap.wapp.core.data.repository.event

import com.wap.wapp.core.model.event.Event
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
}
