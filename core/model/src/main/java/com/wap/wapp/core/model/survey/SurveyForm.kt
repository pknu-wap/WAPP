package com.wap.wapp.core.model.survey

import java.time.LocalDateTime

// 운영진이 등록하는 설문 모델
data class SurveyForm(
    val eventId: String,
    val userId: String,
    val title: String,
    val content: String,
    val surveyQuestion: List<SurveyQuestion>,
    val deadLine: LocalDateTime,
)
