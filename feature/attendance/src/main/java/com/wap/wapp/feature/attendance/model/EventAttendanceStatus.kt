package com.wap.wapp.feature.attendance.model

data class EventAttendanceStatus(
    val content: String,
    val eventId: String,
    val title: String,
    val displayTime: String,
    val isAttendance: Boolean,
)
