package com.wap.wapp.core.domain.usecase.event

import com.wap.wapp.core.data.repository.event.EventRepository
import java.time.LocalDate
import javax.inject.Inject

class PostEventUseCase @Inject constructor(
    private val eventRepository: EventRepository,
) {
    suspend operator fun invoke(
        date: LocalDate,
        eventTitle: String,
        eventContent: String,
        eventLocation: String,
        eventDate: String,
        eventTime: String,
    ): Result<Unit> = runCatching {
        eventRepository.postEvent(
            date = date,
            eventTitle = eventTitle,
            eventContent = eventContent,
            eventLocation = eventLocation,
            eventDate = eventDate,
            eventTime = eventTime,
        )
    }
}
