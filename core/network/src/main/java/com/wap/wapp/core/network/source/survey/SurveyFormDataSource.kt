package com.wap.wapp.core.network.source.survey

import com.wap.wapp.core.network.model.survey.form.SurveyFormRequest
import com.wap.wapp.core.network.model.survey.form.SurveyFormResponse

interface SurveyFormDataSource {
    suspend fun postSurveyForm(surveyFormRequest: SurveyFormRequest): Result<Unit>

    suspend fun getSurveyForm(eventId: String): Result<SurveyFormResponse>

    suspend fun getSurveyFormList(): Result<List<SurveyFormResponse>>
}
