package com.wap.wapp.core.network.source.survey

import com.wap.wapp.core.model.survey.Survey
import com.wap.wapp.core.network.model.survey.SurveyResponse

interface SurveyDataSource {
    suspend fun getSurveyList(): Result<List<SurveyResponse>>

    suspend fun getSurvey(surveyId: String): Result<SurveyResponse>

    suspend fun postSurvey(survey: Survey): Result<Unit>
}
