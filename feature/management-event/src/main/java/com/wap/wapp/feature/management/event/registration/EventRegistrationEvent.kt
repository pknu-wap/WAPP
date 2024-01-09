package com.wap.wapp.feature.management.event.registration

sealed class EventRegistrationEvent {
    data class ValidationError(val message: String) : EventRegistrationEvent()
    data class Failure(val error: Throwable) : EventRegistrationEvent()
    data object Success : EventRegistrationEvent()
}
