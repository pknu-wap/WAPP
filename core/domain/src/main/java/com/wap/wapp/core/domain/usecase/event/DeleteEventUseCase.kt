package com.wap.wapp.core.domain.usecase.event

import com.wap.wapp.core.data.repository.event.EventRepository
import com.wap.wapp.core.model.event.Event
import javax.inject.Inject

class DeleteEventUseCase @Inject constructor(
    private val eventRepository: EventRepository,
) {
    suspend operator fun invoke(eventId: String): Result<List<Event>> =
        eventRepository.getEventList()
}
