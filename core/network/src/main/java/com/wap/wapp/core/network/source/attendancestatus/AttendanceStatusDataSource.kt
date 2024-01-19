package com.wap.wapp.core.network.source.attendancestatus

import com.wap.wapp.core.network.model.attendancestatus.AttendanceStatusResponse

interface AttendanceStatusDataSource {
    suspend fun getAttendanceStatus(
        eventId: String,
        userId: String,
    ): Result<AttendanceStatusResponse>

    suspend fun postAttendanceStatus(eventId: String, userId: String): Result<Unit>
}
