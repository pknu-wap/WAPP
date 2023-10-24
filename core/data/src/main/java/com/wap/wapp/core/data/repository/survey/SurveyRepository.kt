package com.wap.wapp.core.data.repository.survey

import com.wap.wapp.core.model.survey.Survey

interface SurveyRepository {
    suspend fun getSurveyList(): Result<List<Survey>>
}