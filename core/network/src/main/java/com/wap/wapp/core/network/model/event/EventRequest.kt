package com.wap.wapp.core.network.model.event

data class EventRequest(
    val eventTitle: String = "",
    val eventContent: String = "",
    val eventLocation: String = "",
    val eventDate: String = "",
    val eventTime: String = "",
)
