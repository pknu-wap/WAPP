package com.wap.wapp.core.network.source.attendance

import com.wap.wapp.core.network.model.attendance.AttendanceResponse

interface AttendanceDataSource {
    suspend fun postAttendance(
        eventId: String,
        code: String,
        deadline: String,
    ): Result<Unit>

    suspend fun getAttendance(
        eventId: String,
    ): Result<AttendanceResponse>
}
