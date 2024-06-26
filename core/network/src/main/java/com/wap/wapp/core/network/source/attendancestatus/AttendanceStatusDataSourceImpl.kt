package com.wap.wapp.core.network.source.attendancestatus

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.wap.wapp.core.network.constant.ATTENDANCE_STATUS_COLLECTION
import com.wap.wapp.core.network.constant.EVENT_COLLECTION
import com.wap.wapp.core.network.model.attendancestatus.AttendanceStatusRequest
import com.wap.wapp.core.network.model.attendancestatus.AttendanceStatusResponse
import com.wap.wapp.core.network.utils.await
import com.wap.wapp.core.network.utils.generateNowDateTime
import com.wap.wapp.core.network.utils.toISOLocalDateTimeString
import javax.inject.Inject

class AttendanceStatusDataSourceImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
) : AttendanceStatusDataSource {
    override suspend fun getAttendanceStatus(
        eventId: String,
        userId: String,
    ): Result<AttendanceStatusResponse> = runCatching {
        val task = firebaseFirestore.collection(ATTENDANCE_STATUS_COLLECTION)
            .document(userId)
            .collection(EVENT_COLLECTION)
            .document(eventId)
            .get()
            .await()

        // null일 경우 출석이 되지 않음 (출석 시간이 empty로 반환)
        val attendanceStatusResponse = task.toObject<AttendanceStatusResponse>()
        attendanceStatusResponse ?: AttendanceStatusResponse("")
    }

    override suspend fun postAttendanceStatus(eventId: String, userId: String): Result<Unit> =
        runCatching {
            val attendanceStatusRequest =
                AttendanceStatusRequest(generateNowDateTime().toISOLocalDateTimeString())

            firebaseFirestore.collection(ATTENDANCE_STATUS_COLLECTION)
                .document(userId)
                .collection(EVENT_COLLECTION)
                .document(eventId)
                .set(attendanceStatusRequest)
                .await()
        }
}
