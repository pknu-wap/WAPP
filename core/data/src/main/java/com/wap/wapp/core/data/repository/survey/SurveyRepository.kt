package com.wap.wapp.core.data.repository.survey

import com.wap.wapp.core.model.survey.Survey
import com.wap.wapp.core.model.survey.SurveyForm

interface SurveyRepository {
    suspend fun getSurveyList(): Result<List<Survey>>

    suspend fun getSurvey(surveyId: String): Result<Survey>

    suspend fun getSurveyFormList(): Result<List<SurveyForm>>

    suspend fun getSurveyForm(eventId: Int): Result<SurveyForm>
}
