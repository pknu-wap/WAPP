package com.wap.wapp.core.data.repository.survey

import com.wap.wapp.core.model.survey.SurveyForm

interface SurveyFormRepository {
    suspend fun getSurveyForm(eventId: String): Result<SurveyForm>

    suspend fun getSurveyFormList(): Result<List<SurveyForm>>

    suspend fun postSurveyForm(surveyForm: SurveyForm): Result<Unit>
}
