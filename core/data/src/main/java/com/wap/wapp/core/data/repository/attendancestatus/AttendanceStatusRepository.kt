package com.wap.wapp.core.data.repository.attendancestatus

import com.wap.wapp.core.model.attendancestatus.AttendanceStatus

interface AttendanceStatusRepository {
    suspend fun getAttendanceStatus(eventId: String, userId: String): Result<AttendanceStatus>

    suspend fun postAttendance(
        eventId: String,
        userId: String,
    ): Result<Unit>
}
