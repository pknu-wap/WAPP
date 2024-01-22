package com.wap.wapp.core.network.source.attendance

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.wap.wapp.core.network.constant.ATTENDANCE_COLLECTION
import com.wap.wapp.core.network.model.attendance.AttendanceRequest
import com.wap.wapp.core.network.model.attendance.AttendanceResponse
import com.wap.wapp.core.network.utils.await
import javax.inject.Inject

class AttendanceDataSourceImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
) : AttendanceDataSource {
    override suspend fun postAttendance(
        eventId: String,
        code: String,
        deadline: String,
    ): Result<Unit> = runCatching {
        val attendanceRequest = AttendanceRequest(
            eventId = eventId,
            code = code,
            deadline = deadline,
        )

        firebaseFirestore.collection(ATTENDANCE_COLLECTION)
            .document(eventId)
            .set(attendanceRequest)
            .await()
    }

    override suspend fun getAttendance(eventId: String): Result<AttendanceResponse> = runCatching {
        val task = firebaseFirestore.collection(ATTENDANCE_COLLECTION)
            .document(eventId)
            .get()
            .await()

        val attendanceResponse = task.toObject<AttendanceResponse>()
        checkNotNull(attendanceResponse)
    }
}
