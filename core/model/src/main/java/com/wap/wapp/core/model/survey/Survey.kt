package com.wap.wapp.core.model.survey

import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/*
회원이 작성하는 설문 모델
*/

data class Survey(
    val surveyId: String,
    val surveyFormId: String,
    val eventName: String,
    val userName: String,
    val title: String,
    val content: String,
    val surveyAnswerList: List<SurveyAnswer>,
    val surveyedAt: LocalDateTime,
) {
    fun calculateSurveyedAt(): String {
        val currentDateTime = LocalDateTime.now(TIME_ZONE_SEOUL)
        val duration = Duration.between(surveyedAt, currentDateTime)

        if (duration.toMinutes() == 0L) {
            return "방금"
        } else if (duration.toMinutes() < 60) {
            val leftMinutes = duration.toMinutes().toString()
            return leftMinutes + "분 전"
        }

        if (duration.toHours() < 24) {
            val leftHours = duration.toHours().toString()
            return leftHours + "시간 전"
        }

        if (duration.toDays() < 31) {
            val leftDays = duration.toDays().toString()
            return leftDays + "일 전"
        }

        return surveyedAt.format(yyyyMMddFormatter)
    }

    companion object {
        val yyyyMMddFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
        val TIME_ZONE_SEOUL = ZoneId.of("Asia/Seoul")
    }
}
