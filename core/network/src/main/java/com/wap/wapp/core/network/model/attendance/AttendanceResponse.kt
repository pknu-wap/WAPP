package com.wap.wapp.core.network.model.attendance

import com.wap.wapp.core.model.attendance.Attendance
import com.wap.wapp.core.network.utils.toISOLocalDateTime

data class AttendanceResponse(
    val eventId: String = "",
    val code: String = "",
    val deadline: String = "",
) {
    fun toDomain() = Attendance(
        eventId = eventId,
        code = code,
        deadline = deadline.toISOLocalDateTime(),
    )
}
