package com.wap.wapp.core.domain.usecase.attendance

import com.wap.wapp.core.data.repository.attendance.AttendanceRepository
import javax.inject.Inject

class IsVerifyAttendanceCodeUseCase @Inject constructor(
    private val attendanceRepository: AttendanceRepository,
) {
    suspend operator fun invoke(eventId: String, attendanceCode: String): Result<Boolean> =
        runCatching {
            attendanceRepository.getAttendance(eventId).map {
                it.code == attendanceCode
            }.getOrThrow()
        }
}
