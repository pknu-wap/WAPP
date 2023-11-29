package com.wap.wapp.core.domain.usecase.event

import com.wap.wapp.core.data.repository.event.EventRepository
import javax.inject.Inject

class PostEventUseCase @Inject constructor(
    private val eventRepository: EventRepository,
)
