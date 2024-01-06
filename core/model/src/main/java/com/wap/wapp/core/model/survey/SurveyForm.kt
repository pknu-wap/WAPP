package com.wap.wapp.core.model.survey

import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// 운영진이 등록하는 설문 모델
data class SurveyForm(
    val eventId: String,
    val title: String,
    val content: String,
    val surveyQuestionList: List<SurveyQuestion>,
    val deadline: LocalDateTime,
) {
    constructor() : this(
        "",
        "",
        "",
        emptyList(),
        LocalDateTime.MIN,
    )

    fun calculateDeadline(): String {
        val currentDateTime = LocalDateTime.now()
        val duration = Duration.between(currentDateTime, deadline)

        if (duration.toMinutes() < 60) {
            val leftMinutes = duration.toMinutes().toString()
            return leftMinutes + "분 후 마감"
        }

        if (duration.toHours() < 24) {
            val leftHours = duration.toHours().toString()
            return leftHours + "시간 후 마감"
        }

        if (duration.toDays() < 31) {
            val leftDays = duration.toDays().toString()
            return leftDays + "일 후 마감"
        }

        return deadline.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")) + " 마감"
    }

    fun isAfterDeadline(): Boolean {
        val currentDateTime = LocalDateTime.now()
        return deadline.isAfter(currentDateTime)
    }
}
