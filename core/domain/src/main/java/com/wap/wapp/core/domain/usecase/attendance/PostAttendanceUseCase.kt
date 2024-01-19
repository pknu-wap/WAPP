package com.wap.wapp.core.domain.usecase.attendance

import com.wap.wapp.core.data.repository.attendance.AttendanceRepository
import java.time.LocalDateTime
import javax.inject.Inject

class PostAttendanceUseCase @Inject constructor(
    private val attendanceRepository: AttendanceRepository,
) {
    suspend operator fun invoke(
        eventId: String,
        code: String,
        deadline: LocalDateTime,
    ): Result<Unit> =
        attendanceRepository.postAttendance(eventId = eventId, code = code, deadline = deadline)
}
