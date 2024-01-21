package com.wap.wapp.core.model.attendance

import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import kotlin.time.toKotlinDuration

data class Attendance(
    val eventId: String,
    val code: String,
    val deadline: LocalDateTime,
) {
    fun calculateDeadline(): String {
        val currentDateTime = LocalDateTime.now(TIME_ZONE_SEOUL)
        val duration = Duration.between(currentDateTime, deadline)

        val leftMinutes = duration.toKotlinDuration().inWholeMinutes.toString()
        return "${leftMinutes}분 후 마감"
    }

    fun isBeforeEndTime(): Boolean {
        val currentDateTime = LocalDateTime.now(TIME_ZONE_SEOUL)
        return currentDateTime.isBefore(deadline)
    }

    companion object {
        val TIME_ZONE_SEOUL = ZoneId.of("Asia/Seoul")
    }
}
