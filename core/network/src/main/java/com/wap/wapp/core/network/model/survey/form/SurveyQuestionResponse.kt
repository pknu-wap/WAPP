package com.wap.wapp.core.network.model.survey.form

import com.wap.wapp.core.model.survey.SurveyQuestion
import com.wap.wapp.core.network.model.survey.QuestionTypeResponse
import com.wap.wapp.core.network.model.survey.toDomain

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
