package com.wap.wapp.core.network.source.survey

import com.wap.wapp.core.model.survey.SurveyAnswer
import com.wap.wapp.core.network.model.survey.SurveyResponse

interface SurveyDataSource {
    suspend fun isSubmittedSurvey(eventId: Int, userId: String): Result<Boolean>

    suspend fun getSurveyList(): Result<List<SurveyResponse>>

    suspend fun getSurvey(surveyId: String): Result<SurveyResponse>

    suspend fun postSurvey(
        eventId: Int,
        userId: String,
        title: String,
        content: String,
        surveyAnswerList: List<SurveyAnswer>,
        surveyedAt: String,
    ): Result<Unit>
}
