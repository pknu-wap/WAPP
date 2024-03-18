package com.wap.wapp.core.model.attendancestatus

data class AttendanceStatus(
    val attendanceDateTime: String = "",
) {
    fun isAttendance() = attendanceDateTime.isNotEmpty()
}
