package com.wap.wapp.core.network.model.survey

import com.wap.wapp.core.model.survey.SurveyAnswer

data class SurveyRequest(
    val surveyId: String,
    val eventId: Int,
    val userId: String,
    val title: String,
    val content: String,
    val surveyAnswerList: List<SurveyAnswer>,
    val surveyedAt: String,
)
