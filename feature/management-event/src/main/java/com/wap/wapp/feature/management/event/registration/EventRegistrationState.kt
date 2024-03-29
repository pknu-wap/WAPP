package com.wap.wapp.feature.management.event.registration

enum class EventRegistrationState(
    val page: String,
    val progress: Float,
) {
    EVENT_DETAILS("1", 0.5f),
    EVENT_SCHEDULE("2", 1.0f),
}
