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
    fun getCalculatedTime(): String {
        val currentDateTime = LocalDateTime.now(TIME_ZONE_SEOUL)
        if (startDateTime >= currentDateTime) {
            return calculateStartTime()
        }

        if (endDateTime >= currentDateTime) {
            return calculateDeadline()
        }

        return "마감"
    }

    private fun calculateStartTime(): String {
        val currentDateTime = LocalDateTime.now(TIME_ZONE_SEOUL)
        val duration = Duration.between(currentDateTime, startDateTime)

        if (duration.toMinutes() < 60) {
            val leftMinutes = duration.toMinutes().toString()
            return leftMinutes + "분 후 시작"
        }

        if (duration.toHours() < 24) {
            val leftHours = duration.toHours().toString()
            return leftHours + "시간 후 시작"
        }

        return endDateTime.format(yyyyMMddFormatter) + " 시작"
    }

    private fun calculateDeadline(): String {
        val currentDateTime = LocalDateTime.now(TIME_ZONE_SEOUL)
        val duration = Duration.between(currentDateTime, endDateTime)

        if (duration.toMinutes() < 60) {
            val leftMinutes = duration.toMinutes().toString()
            return leftMinutes + "분 후 종료"
        }

        if (duration.toHours() < 24) {
            val leftHours = duration.toHours().toString()
            return leftHours + "시간 후 종료"
        }

        return endDateTime.format(yyyyMMddFormatter) + " 마감"
    }

    fun isBeforeEndTime(): Boolean {
        val currentDateTime = LocalDateTime.now(TIME_ZONE_SEOUL)
        return currentDateTime.isBefore(endDateTime)
    }

    companion object {
        val yyyyMMddFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
        val TIME_ZONE_SEOUL = ZoneId.of("Asia/Seoul")
    }
}
