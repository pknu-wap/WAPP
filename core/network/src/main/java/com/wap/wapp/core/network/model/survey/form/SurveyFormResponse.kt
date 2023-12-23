package com.wap.wapp.core.network.model.survey.form

import com.wap.wapp.core.model.survey.SurveyForm
import com.wap.wapp.core.network.utils.toISOLocalDateTime

data class SurveyFormResponse(
    val eventId: Int,
    val userId: String,
    val title: String,
    val content: String,
    val surveyQuestionList: List<SurveyQuestionResponse>,
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
        title = title,
        content = content,
        surveyQuestionList = surveyQuestionList.map { it.toDomain() },
        deadline = deadline.toISOLocalDateTime(),
    )
}
