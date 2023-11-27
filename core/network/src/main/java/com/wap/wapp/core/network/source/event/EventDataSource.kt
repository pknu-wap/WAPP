package com.wap.wapp.core.network.source.event

import com.wap.wapp.core.network.model.event.EventResponse
import java.time.LocalDate

interface EventDataSource {
    suspend fun getMonthEvents(date: LocalDate): Result<List<EventResponse>>
}
