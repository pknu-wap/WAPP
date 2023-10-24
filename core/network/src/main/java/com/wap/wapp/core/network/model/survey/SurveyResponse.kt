package com.wap.wapp.core.network.model.survey

import com.wap.wapp.core.model.survey.Survey
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class SurveyResponse(
    val noticeId: String,
    val userId: String,
    val title: String,
    val content: String,
    val review: String,
    val feedBack: String,
    val rating: RatingResponse,
    val surveyedAt: String,
) {
    fun toDomain(): Survey = Survey(
        noticeId = noticeId,
        userId = userId,
        title = title,
        content = content,
        review = review,
        feedBack = feedBack,
        rating = rating.toDomain(),
        surveyedAt = LocalDateTime.parse(
            surveyedAt,
            DateTimeFormatter.ISO_LOCAL_DATE_TIME,
        ),
    )
}
