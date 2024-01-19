package com.wap.wapp.core.network.source.attendance

import com.google.firebase.firestore.FirebaseFirestore
import com.wap.wapp.core.network.constant.ATTENDANCE_COLLECTION
import com.wap.wapp.core.network.model.attendance.AttendanceRequest
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
        val documentId = firebaseFirestore.collection(ATTENDANCE_COLLECTION).document().id

        val attendanceRequest = AttendanceRequest(
            eventId = eventId,
            code = code,
            deadline = deadline,
        )

        firebaseFirestore.collection(ATTENDANCE_COLLECTION)
            .document(documentId)
            .set(attendanceRequest)
            .await()
    }
}
