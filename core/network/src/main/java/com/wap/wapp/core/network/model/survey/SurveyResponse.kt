package com.wap.wapp.core.network.model.survey

import com.wap.wapp.core.model.survey.Survey
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class SurveyResponse(
    val surveyId: String,
    val eventId: Int,
    val userId: String,
    val title: String,
    val content: String,
    val surveyAnswerList: List<SurveyAnswerResponse>,
    val surveyedAt: String,
) {
    constructor() : this(
        "",
        -1,
        "",
        "",
        "",
        emptyList<SurveyAnswerResponse>(),
        "",
    )

    fun toDomain(eventName: String, userName: String): Survey = Survey(
        surveyId = surveyId,
        eventName = eventName,
        userName = userName,
        title = this.title,
        content = this.content,
        surveyAnswerList = this.surveyAnswerList.map { answer -> answer.toDomain() },
        surveyedAt = LocalDateTime.parse(
            this.surveyedAt,
            DateTimeFormatter.ISO_LOCAL_DATE_TIME,
        ),
    )
}
