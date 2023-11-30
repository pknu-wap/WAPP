package com.wap.wapp.core.model.survey

data class SurveyAnswer(
    val questionTitle: String,
    val questionAnswer: String,
    val questionType: QuestionType,
)
