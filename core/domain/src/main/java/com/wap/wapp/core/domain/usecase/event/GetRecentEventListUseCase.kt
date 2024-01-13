package com.wap.wapp.core.domain.usecase.event

import com.wap.wapp.core.data.repository.event.EventRepository
import com.wap.wapp.core.model.event.Event
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import javax.inject.Inject

class GetRecentEventListUseCase @Inject constructor(
    private val eventRepository: EventRepository,
) {
    suspend operator fun invoke(registrationDate: LocalDate): Result<List<Event>> {
        val currentDate = LocalDate.now(ZoneId.of("Asia/Seoul"))
        val minimumDate = currentDate.minus(3, ChronoUnit.MONTHS)
        val selectedDate = minOf(registrationDate, minimumDate)
        return eventRepository.getEventListFromDate(selectedDate)
    }
}
