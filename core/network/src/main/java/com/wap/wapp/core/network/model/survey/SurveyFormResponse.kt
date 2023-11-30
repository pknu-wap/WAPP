package com.wap.wapp.core.network.model.survey

import com.wap.wapp.core.model.survey.SurveyForm
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class SurveyFormResponse(
    val eventId: Int,
    val userId: String,
    val title: String,
    val content: String,
    val surveyQuestion: List<SurveyQuestionResponse>,
    val deadline: String,
) {
    constructor() : this(
        -1,
        "",
        "",
        "",
        emptyList<SurveyQuestionResponse>(),
        "",
    )

    fun toDomain(): SurveyForm = SurveyForm(
        eventId = eventId,
        userId = userId,
        title = title,
        content = content,
        surveyQuestion = surveyQuestion.map { it.toDomain() },
        deadline = LocalDateTime.parse(deadline, DateTimeFormatter.ISO_LOCAL_DATE_TIME),
    )
}
