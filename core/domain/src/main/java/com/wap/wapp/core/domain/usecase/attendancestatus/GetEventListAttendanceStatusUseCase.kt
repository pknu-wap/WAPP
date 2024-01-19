package com.wap.wapp.core.domain.usecase.attendancestatus

import com.wap.wapp.core.data.repository.attendancestatus.AttendanceStatusRepository
import com.wap.wapp.core.model.attendancestatus.AttendanceStatus
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class GetEventListAttendanceStatusUseCase @Inject constructor(
    private val attendanceStatusRepository: AttendanceStatusRepository,
) {
    suspend operator fun invoke(
        eventIdList: List<String>,
        userId: String,
    ): Result<List<AttendanceStatus>> = runCatching {
        coroutineScope {
            val deferredList = eventIdList.map { eventId ->
                async {
                    attendanceStatusRepository.getAttendanceStatus(
                        eventId = eventId,
                        userId = userId,
                    )
                }
            }

            deferredList.awaitAll().map {
                it.getOrThrow()
            }
        }
    }
}
