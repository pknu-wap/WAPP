package com.wap.wapp.core.network.model.event

import com.google.firebase.firestore.PropertyName

data class EventRequest(
    val title: String = "",
    val content: String = "",
    val location: String = "",
    val date: String = "",
    val time: String = "",
    @PropertyName("event_id") val id: Int = 0,
)
