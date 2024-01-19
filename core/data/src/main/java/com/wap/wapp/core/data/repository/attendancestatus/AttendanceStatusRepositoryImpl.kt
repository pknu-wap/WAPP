package com.wap.wapp.core.data.repository.attendancestatus

import com.wap.wapp.core.model.attendancestatus.AttendanceStatus
import javax.inject.Inject

class AttendanceStatusRepositoryImpl @Inject constructor() : AttendanceStatusRepository {
    override suspend fun getAttendanceStatus(
        eventId: String,
        userId: String,
    ): Result<AttendanceStatus> {
        TODO("Not yet implemented")
    }

    override suspend fun postAttendance(eventId: String, userId: String): Result<Unit> {
        TODO("Not yet implemented")
    }
}
