package com.wap.wapp.core.network.model.event

data class EventRequest(
    val title: String = "",
    val content: String = "",
    val location: String = "",
    val date: String = "",
    val time: String = "",
)
