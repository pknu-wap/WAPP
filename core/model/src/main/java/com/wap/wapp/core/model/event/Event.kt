package com.wap.wapp.core.model.event

import java.time.LocalDateTime

data class Event(
    val content: String,
    val eventId: String,
    val location: String,
    val title: String,
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime,
)
