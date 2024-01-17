package com.wap.wapp.core.data.repository.survey

import com.wap.wapp.core.model.survey.Survey
import com.wap.wapp.core.model.survey.SurveyAnswer
import java.time.LocalDateTime

interface SurveyRepository {
    suspend fun getSurveyList(): Result<List<Survey>>

    suspend fun getUserRespondedSurveyList(userId: String): Result<List<Survey>>

    suspend fun getSurvey(surveyId: String): Result<Survey>

    suspend fun deleteSurvey(surveyId: String): Result<Survey>

    suspend fun postSurvey(
        surveyFormId: String,
        eventId: String,
        userId: String,
        title: String,
        content: String,
        surveyAnswerList: List<SurveyAnswer>,
        surveyedAt: LocalDateTime,
    ): Result<Unit>

    suspend fun isSubmittedSurvey(surveyFormId: String, userId: String): Result<Boolean>
}
