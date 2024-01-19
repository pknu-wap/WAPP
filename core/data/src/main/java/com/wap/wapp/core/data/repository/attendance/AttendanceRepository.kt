package com.wap.wapp.core.data.repository.attendance

import com.wap.wapp.core.model.attendance.Attendance
import java.time.LocalDateTime

interface AttendanceRepository {
    suspend fun getAttendance(eventId: String): Result<Attendance>

    suspend fun postAttendance(
        eventId: String,
        code: String,
        deadline: LocalDateTime,
    ): Result<Unit>
}
