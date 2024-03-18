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
    // eventIdList를 받아서, 해당 일정에 사용자가 출석을 했는지, 하지 않았는 지 정보를 가져옵니다.
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
