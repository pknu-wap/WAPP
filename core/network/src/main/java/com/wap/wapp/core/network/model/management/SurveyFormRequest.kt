package com.wap.wapp.core.network.model.management

import com.wap.wapp.core.model.survey.SurveyQuestion

data class SurveyFormRequest(
    val userId: String,
    val title: String,
    val content: String,
    val surveyQuestion: List<SurveyQuestion>,
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
