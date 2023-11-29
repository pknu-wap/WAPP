package com.wap.wapp.core.domain.usecase.event

import com.wap.wapp.core.data.repository.event.EventRepository
import javax.inject.Inject

class PostEventUseCase @Inject constructor(
    private val eventRepository: EventRepository,
) {
    suspend operator fun invoke(
        eventTitle: String,
        eventContent: String,
        eventLocation: String,
        eventDate: String,
        eventTime: String,
    ): Result<Unit> = runCatching {
        eventRepository.postEvent(
            eventTitle = eventTitle,
            eventContent = eventContent,
            eventLocation = eventLocation,
            eventDate = eventDate,
            eventTime = eventTime,
        )
    }
}
