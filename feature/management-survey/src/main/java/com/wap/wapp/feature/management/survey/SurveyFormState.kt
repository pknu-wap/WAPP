package com.wap.wapp.feature.management.survey

enum class SurveyFormState(
    val page: String,
    val progress: Float,
) {
    EVENT_SELECTION("1", 0.25f),
    INFORMATION("2", 0.50f),
    QUESTION("3", 0.75f),
    DEADLINE("4", 1f),
}
