package com.wap.wapp.core.model.survey

import java.time.LocalDateTime

/*
회원이 작성하는 설문 모델
*/

data class Survey(
    val surveyId: String,
    val eventName: String,
    val userName: String,
    val title: String,
    val content: String,
    val surveyAnswerList: List<SurveyAnswer>,
    val surveyedAt: LocalDateTime,
)
