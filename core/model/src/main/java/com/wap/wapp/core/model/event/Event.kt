package com.wap.wapp.core.model.event

data class Event(
    val content: String,
    val eventId: Int,
    val location: String,
    val period: String,
    val title: String,
)
