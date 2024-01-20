package com.wap.wapp.core.network.source.event

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.wap.wapp.core.network.constant.EVENT_COLLECTION
import com.wap.wapp.core.network.model.event.EventRequest
import com.wap.wapp.core.network.model.event.EventResponse
import com.wap.wapp.core.network.utils.await
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class EventDataSourceImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
) : EventDataSource {
    override suspend fun getEventList(): Result<List<EventResponse>> = runCatching {
        val result = mutableListOf<EventResponse>()

        val task = firebaseFirestore.collection(EVENT_COLLECTION)
            .get()
            .await()

        for (document in task.documents) {
            val event = document.toObject<EventResponse>()
            checkNotNull(event)
            result.add(event)
        }

        result
    }

    override suspend fun getEventListFromDate(date: LocalDate): Result<List<EventResponse>> =
        runCatching {
            val result = mutableListOf<EventResponse>()

            // 선택된 날짜 1일 00시 00분 00초
            val startDateTime = date.atStartOfDay().toISOLocalDateTimeString()

            val task = firebaseFirestore.collection(EVENT_COLLECTION)
                .whereGreaterThanOrEqualTo("startDateTime", startDateTime)
                .get()
                .await()

            for (document in task.documents) {
                val event = document.toObject<EventResponse>()
                checkNotNull(event)
                result.add(event)
            }

            result
        }

    override suspend fun getMonthEventList(date: LocalDate): Result<List<EventResponse>> =
        runCatching {
            val result = mutableListOf<EventResponse>()

            // 선택된 날짜 1일 00시 00분 00초
            val startDateTime = LocalDate.of(date.year, date.month, 1).atStartOfDay()
                .toISOLocalDateTimeString()

            // 선택된 날짜의 마지막 날(31일) 23시 59분 59초
            val endDateTime =
                LocalDate.of(date.year, date.month, date.lengthOfMonth()).atTime(LocalTime.MAX)
                    .toISOLocalDateTimeString()

            val task = firebaseFirestore.collection(EVENT_COLLECTION)
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

    override suspend fun getDateEventList(date: LocalDate): Result<List<EventResponse>> =
        runCatching {
            val result = mutableListOf<EventResponse>()

            val startDateTime = date.atStartOfDay().toISOLocalDateTimeString()
            val endDateTime = date.atTime(LocalTime.MAX).toISOLocalDateTimeString()

            val task = firebaseFirestore.collection(EVENT_COLLECTION)
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

    override suspend fun getEvent(eventId: String): Result<EventResponse> = runCatching {
        val document = firebaseFirestore.collection(EVENT_COLLECTION)
            .document(eventId)
            .get()
            .await()

        checkNotNull(document.toObject<EventResponse>())
    }

    override suspend fun deleteEvent(eventId: String): Result<Unit> = runCatching {
        firebaseFirestore.collection(EVENT_COLLECTION)
            .document(eventId)
            .delete()
            .await()
    }

    override suspend fun postEvent(
        title: String,
        content: String,
        location: String,
        startDateTime: String,
        endDateTime: String,
    ): Result<Unit> = runCatching {
        val documentId = firebaseFirestore.collection(EVENT_COLLECTION).document().id

        val eventRequest = EventRequest(
            title = title,
            content = content,
            location = location,
            startDateTime = startDateTime,
            endDateTime = endDateTime,
            eventId = documentId,
        )

        firebaseFirestore.collection(EVENT_COLLECTION)
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
        val updateData = mapOf(
            "title" to title,
            "content" to content,
            "location" to location,
            "startDateTime" to startDateTime,
            "endDateTime" to endDateTime,
        )

        firebaseFirestore.collection(EVENT_COLLECTION)
            .document(eventId)
            .update(updateData)
            .await()
    }
}

private fun LocalDateTime.toISOLocalDateTimeString(): String =
    this.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
