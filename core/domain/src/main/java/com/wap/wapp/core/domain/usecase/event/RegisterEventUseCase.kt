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
        date: LocalDate,
        eventTitle: String,
        eventContent: String,
        eventLocation: String,
        eventDate: LocalDate,
        eventTime: LocalTime,
    ): Result<Unit> = runCatching {
        eventRepository.postEvent(
            date = date,
            eventTitle = eventTitle,
            eventContent = eventContent,
            eventLocation = eventLocation,
            eventDateTime = LocalDateTime.of(eventDate, eventTime),
        )
    }
}
