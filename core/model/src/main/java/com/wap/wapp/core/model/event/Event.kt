package com.wap.wapp.core.model.event

import java.time.LocalDate

data class Event(
    val content: String,
    val eventId: Int,
    val location: String,
    val period: LocalDate,
    val title: String,
)
