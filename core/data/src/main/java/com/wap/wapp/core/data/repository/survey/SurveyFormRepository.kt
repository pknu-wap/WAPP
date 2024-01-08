package com.wap.wapp.core.data.repository.survey

import com.wap.wapp.core.model.survey.SurveyForm
import com.wap.wapp.core.model.survey.SurveyQuestion
import java.time.LocalDateTime

interface SurveyFormRepository {
    suspend fun getSurveyForm(surveyFormId: String): Result<SurveyForm>

    suspend fun getSurveyFormList(): Result<List<SurveyForm>>

    suspend fun postSurveyForm(
        eventId: String,
        title: String,
        content: String,
        surveyQuestionList: List<SurveyQuestion>,
        deadline: LocalDateTime,
    ): Result<Unit>
}
