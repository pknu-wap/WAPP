package com.wap.wapp.core.network.source.survey

import com.wap.wapp.core.network.model.management.SurveyFormRequest
import com.wap.wapp.core.network.model.survey.SurveyFormResponse

interface SurveyFormDataSource {
    suspend fun postSurveyForm(surveyFormRequest: SurveyFormRequest, eventId: Int): Result<Unit>

    suspend fun getSurveyForm(eventId: Int): Result<SurveyFormResponse>

    suspend fun getSurveyFormList(): Result<List<SurveyFormResponse>>
}
