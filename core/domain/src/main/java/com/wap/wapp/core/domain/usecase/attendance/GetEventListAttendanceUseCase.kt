package com.wap.wapp.core.domain.usecase.attendance

import com.wap.wapp.core.data.repository.attendance.AttendanceRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class GetEventListAttendanceUseCase @Inject constructor(
    private val attendanceRepository: AttendanceRepository,
) {
    suspend operator fun invoke(eventIdList: List<String>) =
        runCatching {
            coroutineScope {
                val deferredList = eventIdList.map { eventId ->
                    async { attendanceRepository.getAttendance(eventId = eventId) }
                }

                deferredList.awaitAll().map {
                    it.getOrThrow()
                }
            }
        }
}
