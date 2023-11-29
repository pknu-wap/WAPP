package com.wap.wapp.core.network.source.event

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.wap.wapp.core.network.constant.EVENT_COLLECTION
import com.wap.wapp.core.network.model.event.EventRequest
import com.wap.wapp.core.network.model.event.EventResponse
import com.wap.wapp.core.network.utils.await
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class EventDataSourceImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
) : EventDataSource {
    override suspend fun getMonthEvents(date: LocalDate): Result<List<EventResponse>> =
        runCatching {
            val result = mutableListOf<EventResponse>()

            val task = firebaseFirestore.collection(EVENT_COLLECTION)
                .document(getMonth(date))
                .collection(EVENT_COLLECTION)
                .get()
                .await()

            for (document in task.documents) {
                val event = document.toObject<EventResponse>()
                checkNotNull(event)
                result.add(event)
            }

            result
        }

    override suspend fun postEvent(date: LocalDate, eventRequest: EventRequest): Result<Unit> =
        runCatching {
            firebaseFirestore.collection(EVENT_COLLECTION)
                .document(getMonth(date))
                .collection(EVENT_COLLECTION)
                .document()
                .set(eventRequest)
                .await()
        }

    private fun getMonth(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM")

        return date.format(formatter)
    }
}
