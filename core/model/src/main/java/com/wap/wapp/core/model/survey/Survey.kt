package com.wap.wapp.core.model.survey

import java.time.LocalDateTime

data class Survey(
    val surveyId: String,
    val noticeName: String,
    val userName: String,
    val title: String,
    val content: String,
    val review: String,
    val feedBack: String,
    val rating: Rating,
    val surveyedAt: LocalDateTime,
)
