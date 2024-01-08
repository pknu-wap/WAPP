package com.wap.wapp.core.network.model.survey.form

import com.wap.wapp.core.model.survey.SurveyForm
import com.wap.wapp.core.network.utils.toISOLocalDateTime

data class SurveyFormResponse(
    val surveyFormId: String,
    val eventId: String,
    val userId: String,
    val title: String,
    val content: String,
    val surveyQuestionList: List<SurveyQuestionResponse>,
    val deadline: String,
) {
    constructor() : this(
        "",
        "",
        "",
        "",
        "",
        emptyList<SurveyQuestionResponse>(),
        "",
    )

    fun toDomain(): SurveyForm = SurveyForm(
        surveyFormId = surveyFormId,
        eventId = eventId,
        title = title,
        content = content,
        surveyQuestionList = surveyQuestionList.map { it.toDomain() },
        deadline = deadline.toISOLocalDateTime(),
    )
}
