package com.wap.wapp.core.network.model.attendancestatus

import com.wap.wapp.core.model.attendancestatus.AttendanceStatus

data class AttendanceStatusResponse(
    val attendanceDateTime: String = "",
) {
    fun toDomain() = AttendanceStatus(attendanceDateTime)
}
