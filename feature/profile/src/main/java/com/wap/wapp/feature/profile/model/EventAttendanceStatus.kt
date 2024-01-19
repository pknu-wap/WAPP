package com.wap.wapp.feature.profile.model

import java.time.LocalDateTime

data class EventAttendanceStatus(
    val content: String,
    val eventId: String,
    val title: String,
    val startDateTime: LocalDateTime,
    val isAttendance: Boolean = false,
)
