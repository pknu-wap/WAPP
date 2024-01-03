package com.wap.wapp.core.network.model.survey.form

import com.wap.wapp.core.model.survey.SurveyQuestion

data class SurveyFormRequest(
    val eventId: String,
    val title: String,
    val content: String,
    val surveyQuestionList: List<SurveyQuestion>,
    val deadline: String,
) {
    constructor() : this(
        "",
        "",
        "",
        emptyList(),
        "",
    )
}
