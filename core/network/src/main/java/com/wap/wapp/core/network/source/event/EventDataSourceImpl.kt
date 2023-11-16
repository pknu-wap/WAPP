package com.wap.wapp.core.network.source.event

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.wap.wapp.core.network.constant.EVENT_COLLECTION
import com.wap.wapp.core.network.model.event.EventResponse
import com.wap.wapp.core.network.utils.await
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class EventDataSourceImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
) : EventDataSource {
    override suspend fun getNowMonthEvents(): Result<List<EventResponse>> = runCatching {
        val nowMonth = getNowMonth()
        val result = mutableListOf<EventResponse>()

        val task = firebaseFirestore.collection(EVENT_COLLECTION)
            .whereGreaterThanOrEqualTo("period", nowMonth)
            .whereLessThanOrEqualTo("period", nowMonth + "~")
            .get()
            .await()

        for (document in task.documents) {
            val event = document.toObject<EventResponse>()
            checkNotNull(event)
            result.add(event)
        }
        result
    }

    private fun getNowMonth(): String {
        val nowTime = LocalDateTime.now()
        // 2023-11 형식 포맷
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM")
        return nowTime.format(formatter)
    }
}
