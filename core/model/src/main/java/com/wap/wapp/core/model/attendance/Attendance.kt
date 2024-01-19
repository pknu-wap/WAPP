package com.wap.wapp.core.model.attendance

import com.wap.wapp.core.model.util.DateUtil.generateNowDateTime
import java.time.Duration
import java.time.LocalDateTime
import kotlin.time.toKotlinDuration

data class Attendance(
    val eventId: String,
    val code: String,
    val deadline: LocalDateTime,
) {
    fun calculateDeadline(): String {
        val currentDateTime = generateNowDateTime()
        val duration = Duration.between(currentDateTime, deadline)

        val leftMinutes = (duration.toKotlinDuration().inWholeSeconds / 60).toString()
        val leftSeconds = (duration.toKotlinDuration().inWholeSeconds % 60).toString()
        return "${leftMinutes}분 ${leftSeconds}초 후 마감"
    }

    fun isBeforeEndTime(): Boolean {
        val currentDateTime = generateNowDateTime()
        return currentDateTime.isBefore(deadline)
    }
}
