package com.wap.wapp.core.network.model.attendance

data class AttendanceRequest(
    val eventId: String = "",
    val code: String = "",
    val deadline: String = "",
)
