package com.wap.wapp.core.network.source.survey

import com.wap.wapp.core.model.survey.SurveyQuestion
import com.wap.wapp.core.network.model.survey.form.SurveyFormRequest
import com.wap.wapp.core.network.model.survey.form.SurveyFormResponse

interface SurveyFormDataSource {
    suspend fun postSurveyForm(
        eventId: String,
        title: String,
        content: String,
        surveyQuestionList: List<SurveyQuestion>,
        deadline: String,
    ): Result<Unit>

    suspend fun getSurveyForm(surveyFormId: String): Result<SurveyFormResponse>

    suspend fun getSurveyFormList(): Result<List<SurveyFormResponse>>

    suspend fun updateSurveyForm(surveyFormRequest: SurveyFormRequest): Result<Unit>
}
