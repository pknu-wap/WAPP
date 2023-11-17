package com.wap.wapp.core.data.repository.event

import com.wap.wapp.core.model.event.Event

interface EventRepository {
    suspend fun getNowMonthEvents(): Result<List<Event>>
}
