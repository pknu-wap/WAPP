package com.wap.wapp.core.domain.usecase.attendancestatus

import com.wap.wapp.core.data.repository.attendancestatus.AttendanceStatusRepository
import javax.inject.Inject

class PostAttendanceStatusUseCase @Inject constructor(
    private val attendanceStatusRepository: AttendanceStatusRepository,
) {
    suspend operator fun invoke(eventId: String, userId: String): Result<Unit> =
        attendanceStatusRepository.postAttendance(eventId = eventId, userId = userId)
}
