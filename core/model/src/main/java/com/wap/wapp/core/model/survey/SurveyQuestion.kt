package com.wap.wapp.core.model.survey

data class SurveyQuestion(
    val questionTitle: String,
    val questionType: QuestionType,
)

enum class QuestionType {
    MULTIPLE_CHOICE, ESSAY
}
