package com.wap.wapp.core.network.source.event

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.wap.wapp.core.network.constant.EVENT_COLLECTION
import com.wap.wapp.core.network.constant.SURVEY_COLLECTION
import com.wap.wapp.core.network.model.event.EventRequest
import com.wap.wapp.core.network.model.event.EventResponse
import com.wap.wapp.core.network.utils.await
import com.wap.wapp.core.network.utils.toISOLocalDateTime
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
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

    override suspend fun getDateEvents(date: LocalDate): Result<List<EventResponse>> =
        runCatching {
            val result = mutableListOf<EventResponse>()

            val startDateTime = date.atStartOfDay()
            val endDateTime = date.atTime(LocalTime.MAX)

            val task = firebaseFirestore.collection(EVENT_COLLECTION)
                .document(getMonth(date))
                .collection(EVENT_COLLECTION)
                .whereGreaterThanOrEqualTo("startDateTime", startDateTime)
                .whereLessThanOrEqualTo("startDateTime", endDateTime)
                .get()
                .await()

            for (document in task.documents) {
                val event = document.toObject<EventResponse>()
                checkNotNull(event)
                result.add(event)
            }

            result
        }

    override suspend fun getEvent(date: LocalDateTime, eventId: String): Result<EventResponse> =
        runCatching {
            val document = firebaseFirestore.collection(EVENT_COLLECTION)
                .document(getMonth(date.toLocalDate()))
                .collection(EVENT_COLLECTION)
                .document(eventId)
                .get()
                .await()

            checkNotNull(document.toObject<EventResponse>())
        }

    override suspend fun postEvent(
        title: String,
        content: String,
        location: String,
        startDateTime: String,
        endDateTime: String,
    ): Result<Unit> = runCatching {
        val documentId = firebaseFirestore.collection(SURVEY_COLLECTION).document().id

        val eventRequest = EventRequest(
            title = title,
            content = content,
            location = location,
            startDateTime = startDateTime,
            endDateTime = endDateTime,
            eventId = documentId,
        )

        val startDate = startDateTime.toISOLocalDateTime().toLocalDate()

        firebaseFirestore.collection(EVENT_COLLECTION)
            .document(getMonth(startDate))
            .collection(EVENT_COLLECTION)
            .document(documentId)
            .set(eventRequest)
            .await()
    }

    override suspend fun updateEvent(
        eventId: String,
        title: String,
        content: String,
        location: String,
        startDateTime: String,
        endDateTime: String,
    ): Result<Unit> = runCatching {
        val startDate = startDateTime.toISOLocalDateTime().toLocalDate()
        val updateData = mapOf(
            "title" to title,
            "content" to content,
            "location" to location,
            "startDateTime" to startDateTime,
            "endDateTime" to endDateTime,
        )

        firebaseFirestore.collection(EVENT_COLLECTION)
            .document(getMonth(startDate))
            .collection(EVENT_COLLECTION)
            .document(eventId)
            .update(updateData)
            .await()
    }

    private fun getMonth(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM")

        return date.format(formatter)
    }
}
