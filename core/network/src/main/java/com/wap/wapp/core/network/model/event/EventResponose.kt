package com.wap.wapp.core.network.model.event

data class EventResponose(
    val content: String,
    val eventId: Int,
    val location: String,
    val period: String,
    val title: String,
)
