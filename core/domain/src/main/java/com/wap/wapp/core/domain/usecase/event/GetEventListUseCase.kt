package com.wap.wapp.core.domain.usecase.event

import com.wap.wapp.core.data.repository.event.EventRepository
import com.wap.wapp.core.model.event.Event
import java.time.LocalDate
import javax.inject.Inject

class GetEventListUseCase @Inject constructor(
    private val eventRepository: EventRepository,
) {
    suspend operator fun invoke(date: LocalDate): Result<List<Event>> = runCatching {
        eventRepository.getMonthEvents(date).fold(
            onSuccess = { eventsList -> eventsList },
            onFailure = { throw (it) },
        )
    }
}
