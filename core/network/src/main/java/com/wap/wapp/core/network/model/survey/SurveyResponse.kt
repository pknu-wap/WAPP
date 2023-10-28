package com.wap.wapp.core.network.model.survey

import com.wap.wapp.core.model.survey.Survey
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class SurveyResponse(
    val surveyId: String,
    val noticeId: String,
    val userId: String,
    val title: String,
    val content: String,
    val review: String,
    val feedBack: String,
    val rating: RatingResponse,
    val surveyedAt: String,
) {
    constructor() : this(
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        RatingResponse.GOOD,
        "",
    )

    fun toDomain(noticeName: String, userName: String): Survey = Survey(
        surveyId = this.surveyId,
        noticeName = noticeName,
        userName = userName,
        title = this.title,
        content = this.content,
        review = this.review,
        feedBack = this.feedBack,
        rating = this.rating.toDomain(),
        surveyedAt = LocalDateTime.parse(
            this.surveyedAt,
            DateTimeFormatter.ISO_LOCAL_DATE_TIME,
        ),
    )
}
