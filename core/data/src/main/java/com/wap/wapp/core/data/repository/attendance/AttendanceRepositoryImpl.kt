package com.wap.wapp.core.data.repository.attendance

import com.wap.wapp.core.data.utils.toISOLocalDateTimeString
import com.wap.wapp.core.model.attendance.Attendance
import com.wap.wapp.core.network.source.attendance.AttendanceDataSource
import java.time.LocalDateTime
import javax.inject.Inject

class AttendanceRepositoryImpl @Inject constructor(
    private val attendanceDataSource: AttendanceDataSource,
) : AttendanceRepository {
    override suspend fun getAttendance(eventId: String): Result<Attendance> =
        attendanceDataSource.getAttendance(eventId).mapCatching { attendanceResponse ->
            attendanceResponse.toDomain()
        }

    override suspend fun postAttendance(
        eventId: String,
        code: String,
        deadline: LocalDateTime,
    ): Result<Unit> =
        attendanceDataSource.postAttendance(
            eventId = eventId,
            code = code,
            deadline = deadline.toISOLocalDateTimeString(),
        )
}
