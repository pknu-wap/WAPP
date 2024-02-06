package com.wap.wapp.feature.management.survey

import com.wap.wapp.core.model.event.Event

sealed class EventsState {
    data object Loading : EventsState()
    data class Success(val events: List<Event>) : EventsState()
    data class Failure(val throwable: Throwable) : EventsState()
}
