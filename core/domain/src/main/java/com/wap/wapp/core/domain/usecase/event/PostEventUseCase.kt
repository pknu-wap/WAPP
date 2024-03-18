package com.wap.wapp.core.domain.usecase.event

import com.wap.wapp.core.data.repository.event.EventRepository
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

class PostEventUseCase @Inject constructor(
    private val eventRepository: EventRepository,
) {
    suspend operator fun invoke(
        eventTitle: String,
        eventContent: String,
        eventLocation: String,
        eventStartDate: LocalDate,
        eventStartTime: LocalTime,
        eventEndDate: LocalDate,
        eventEndTime: LocalTime,
    ): Result<Unit> = runCatching {
        eventRepository.postEvent(
            title = eventTitle,
            content = eventContent,
            location = eventLocation,
            startDateTime = LocalDateTime.of(eventStartDate, eventStartTime),
            endDateTime = LocalDateTime.of(eventEndDate, eventEndTime),
        )
    }
}
