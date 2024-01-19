package com.wap.wapp.core.model.survey

import com.wap.wapp.core.model.util.DateUtil.generateNowDateTime
import com.wap.wapp.core.model.util.DateUtil.yyyyMMddFormatter
import java.time.Duration
import java.time.LocalDateTime

// 운영진이 등록하는 설문 모델
data class SurveyForm(
    val surveyFormId: String,
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
        "",
        emptyList(),
        LocalDateTime.MIN,
    )

    fun calculateDeadline(): String {
        val currentDateTime = generateNowDateTime()
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

        return deadline.format(yyyyMMddFormatter) + " 마감"
    }

    fun isBeforeDeadline(): Boolean {
        val currentDateTime = generateNowDateTime()
        return currentDateTime.isBefore(deadline)
    }
}
