package com.wap.wapp.feature.management.survey

enum class SurveyRegistrationState(
    val page: String,
    val progress: Float,
) {
    INFORMATION("1", 0.33f),
    QUESTION("2", 0.66f),
    DEADLINE("3", 1f),
}
