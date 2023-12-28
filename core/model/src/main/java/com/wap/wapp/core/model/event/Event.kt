package com.wap.wapp.core.model.event

import java.time.LocalDateTime

data class Event(
    val content: String,
    val eventId: Int,
    val location: String,
    val title: String,
    val dateTime: LocalDateTime,
)
