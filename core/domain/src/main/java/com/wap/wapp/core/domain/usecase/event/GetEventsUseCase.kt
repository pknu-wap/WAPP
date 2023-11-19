package com.wap.wapp.core.domain.usecase.event

import android.util.Log
import com.wap.wapp.core.data.repository.event.EventRepository
import com.wap.wapp.core.model.event.Event
import javax.inject.Inject

class GetEventsUseCase @Inject constructor(
    private val eventRepository: EventRepository,
) {
    suspend operator fun invoke(): Result<List<Event>> {
        val result = eventRepository.getNowMonthEvents()
        Log.d("test", "getEventUseCase : $result")
        return result
    }
}
