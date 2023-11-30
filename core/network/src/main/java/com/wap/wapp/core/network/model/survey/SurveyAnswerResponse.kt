package com.wap.wapp.core.network.model.survey

import com.wap.wapp.core.model.survey.QuestionType
import com.wap.wapp.core.model.survey.SurveyAnswer

data class SurveyAnswerResponse(
    val questionTitle: String,
    val questionAnswer: String,
    val questionType: QuestionTypeResponse,
) {
    constructor() : this(
        "",
        "",
        QuestionTypeResponse.SUBJECTIVE,
    )

    fun toDomain() = SurveyAnswer(
        questionTitle = questionTitle,
        questionAnswer = questionAnswer,
        questionType = questionType.toDomain(),
    )
}

enum class QuestionTypeResponse {
    OBJECTIVE, SUBJECTIVE
}

internal fun QuestionTypeResponse.toDomain(): QuestionType = when (this) {
    QuestionTypeResponse.SUBJECTIVE -> { QuestionType.SUBJECTIVE }
    QuestionTypeResponse.OBJECTIVE -> { QuestionType.OBJECTIVE }
}
