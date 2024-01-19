package com.wap.wapp.core.network.source.attendance

interface AttendanceDataSource {
    suspend fun postAttendance(
        eventId: String,
        code: String,
        deadline: String,
    ): Result<Unit>
}
