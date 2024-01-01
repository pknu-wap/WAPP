package com.wap.wapp.core.domain.usecase.event

import com.wap.wapp.core.data.repository.event.EventRepository
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

class RegisterEventUseCase @Inject constructor(
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
            eventTitle = eventTitle,
            eventContent = eventContent,
            eventLocation = eventLocation,
            eventStartDateTime = LocalDateTime.of(eventStartDate, eventStartTime),
            eventEndDateTime = LocalDateTime.of(eventEndDate, eventEndTime),
        )
    }
}
