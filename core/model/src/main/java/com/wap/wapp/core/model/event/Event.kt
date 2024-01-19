package com.wap.wapp.core.model.event

import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

data class Event(
    val content: String,
    val eventId: String,
    val location: String,
    val title: String,
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime,
) {
    fun calculateStart(): String {
        val zoneId = ZoneId.of("Asia/Seoul")
        val currentDateTime = LocalDateTime.now(zoneId)
        val duration = Duration.between(currentDateTime, startDateTime)

        if (duration.toMinutes() < 60) {
            val leftMinutes = duration.toMinutes().toString()
            return leftMinutes + "분 후 시작"
        }

        if (duration.toHours() < 24) {
            val leftHours = duration.toHours().toString()
            return leftHours + "시간 후 시작"
        }

        return endDateTime.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")) + " 마감"
    }

    fun calculateDeadline(): String {
        val zoneId = ZoneId.of("Asia/Seoul")
        val currentDateTime = LocalDateTime.now(zoneId)
        val duration = Duration.between(currentDateTime, endDateTime)

        if (duration.toMinutes() < 60) {
            val leftMinutes = duration.toMinutes().toString()
            return leftMinutes + "분 후 종료"
        }

        if (duration.toHours() < 24) {
            val leftHours = duration.toHours().toString()
            return leftHours + "시간 후 종료"
        }

        return endDateTime.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")) + " 마감"
    }
}
