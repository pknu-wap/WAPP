package com.wap.wapp.core.model.survey

import java.time.LocalDateTime

// 운영진이 등록하는 설문 모델
data class SurveyForm(
    val eventId: Int,
    val title: String,
    val content: String,
    val surveyQuestionList: List<SurveyQuestion>,
    val deadline: LocalDateTime,
) {
    constructor() : this(
        -1,
        "",
        "",
        emptyList(),
        LocalDateTime.MIN,
    )
}
