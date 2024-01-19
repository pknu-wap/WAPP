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
        val zoneId = ZoneId.of("Asia/Seoul")
        val currentDateTime = LocalDateTime.now(zoneId)
        val duration = Duration.between(currentDateTime, deadline)

        val leftMinutes = (duration.toKotlinDuration().inWholeSeconds / 60).toString()
        val leftSeconds = (duration.toKotlinDuration().inWholeSeconds % 60).toString()
        return "${leftMinutes}분 ${leftSeconds}초 후 마감"
    }
}
