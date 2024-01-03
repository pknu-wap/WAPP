package com.wap.wapp.core.model.event

import java.time.LocalDate

data class Event(
    val content: String,
    val eventId: String,
    val location: String,
    val period: LocalDate,
    val title: String,
    val time: String = "",
)
