package com.wap.wapp.core.data.repository.attendancestatus

import com.wap.wapp.core.model.attendancestatus.AttendanceStatus
import com.wap.wapp.core.network.source.attendancestatus.AttendanceStatusDataSource
import javax.inject.Inject

class AttendanceStatusRepositoryImpl @Inject constructor(
    private val attendanceStatusDataSource: AttendanceStatusDataSource,
) : AttendanceStatusRepository {
    override suspend fun getAttendanceStatus(
        eventId: String,
        userId: String,
    ): Result<AttendanceStatus> = attendanceStatusDataSource.getAttendanceStatus(
        eventId = eventId,
        userId = userId,
    ).mapCatching { it.toDomain() }

    override suspend fun postAttendance(eventId: String, userId: String): Result<Unit> =
        attendanceStatusDataSource.postAttendanceStatus(eventId = eventId, userId = userId)
}
