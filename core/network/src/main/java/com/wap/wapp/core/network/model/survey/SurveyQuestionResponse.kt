package com.wap.wapp.core.network.model.survey

import com.wap.wapp.core.model.survey.SurveyQuestion

data class SurveyQuestionResponse(
    val questionTitle: String,
    val questionType: QuestionTypeResponse,
) {
    constructor() : this(
        "",
        QuestionTypeResponse.SUBJECTIVE,
    )
    fun toDomain() = SurveyQuestion(
        questionTitle = questionTitle,
        questionType = questionType.toDomain(),
    )
}
