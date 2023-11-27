package com.wap.wapp.core.data.repository.event

import com.wap.wapp.core.model.event.Event
import java.time.LocalDate

interface EventRepository {
    suspend fun getMonthEvents(date: LocalDate): Result<List<Event>>
}
