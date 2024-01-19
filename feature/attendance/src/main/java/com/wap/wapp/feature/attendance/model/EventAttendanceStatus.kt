package com.wap.wapp.feature.attendance.model

data class EventAttendanceStatus(
    val content: String,
    val eventId: String,
    val title: String,
    val remainAttendanceDateTime: String,
    val isAttendance: Boolean,
)
