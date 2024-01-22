package com.wap.wapp.core.domain.usecase.attendance

import com.wap.wapp.core.data.repository.attendance.AttendanceRepository
import javax.inject.Inject

class GetAttendanceUseCase @Inject constructor(
    private val attendanceRepository: AttendanceRepository,
) {
    suspend operator fun invoke(eventId: String) = attendanceRepository.getAttendance(eventId)
}
