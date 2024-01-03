package com.wap.wapp.core.network.model.event

data class EventRequest(
    val title: String = "",
    val content: String = "",
    val location: String = "",
    val startDateTime: String = "",
    val endDateTime: String = "",
    val eventId: String = "",
)
