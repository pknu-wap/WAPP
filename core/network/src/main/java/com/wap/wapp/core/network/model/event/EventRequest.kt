package com.wap.wapp.core.network.model.event

data class EventRequest(
    val title: String = "",
    val content: String = "",
    val location: String = "",
    val period: String = "",
    val time: String = "",
    val eventId: Int = 0,
)
