package com.wap.wapp.core.domain.usecase.event

import com.wap.wapp.core.data.repository.event.EventRepository
import java.time.LocalDateTime
import javax.inject.Inject

class GetEventUseCase @Inject constructor(
    private val eventRepository: EventRepository,
) {
    suspend operator fun invoke(date: LocalDateTime, eventId: String) =
        eventRepository.getEvent(date = date, eventId = eventId)
}
