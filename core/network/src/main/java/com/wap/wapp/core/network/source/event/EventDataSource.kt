package com.wap.wapp.core.network.source.event

import com.wap.wapp.core.network.model.event.EventResponse

interface EventDataSource{
    suspend fun getEvents() : Result<List<EventResponse>>
}
