package com.wap.wapp.core.domain.usecase.attendance

import com.wap.wapp.core.data.repository.attendance.AttendanceRepository
import com.wap.wapp.core.model.attendance.Attendance
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class GetEventListAttendanceUseCase @Inject constructor(
    private val attendanceRepository: AttendanceRepository,
) {
    // 오늘 일정 중, 출석이 시작된 일정들을 가져옵니다.
    suspend operator fun invoke(eventIdList: List<String>): Result<List<Attendance>> =
        runCatching {
            coroutineScope {
                val deferredList = eventIdList.map { eventId ->
                    async { attendanceRepository.getAttendance(eventId = eventId) }
                }

                deferredList.awaitAll()
                    .map { it.getOrThrow() }
                    .filter { it.isBeforeEndTime() }
            }
        }
}
