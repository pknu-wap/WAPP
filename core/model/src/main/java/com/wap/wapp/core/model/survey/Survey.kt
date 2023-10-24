package com.wap.wapp.core.model.survey

import java.time.LocalDateTime

data class Survey(
    val noticeId: String,
    val userId: String,
    val title: String,
    val content: String,
    val review: String,
    val feedBack: String,
    val rating: Rating,
    val surveyedAt: LocalDateTime,
)
