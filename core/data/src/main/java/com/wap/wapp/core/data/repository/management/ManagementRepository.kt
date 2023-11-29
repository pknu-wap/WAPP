package com.wap.wapp.core.data.repository.management

import com.wap.wapp.core.model.survey.SurveyForm

interface ManagementRepository {
    suspend fun getManager(userId: String): Result<Boolean>

    suspend fun postManager(userId: String): Result<Unit>

    suspend fun getManagementCode(code: String): Result<Boolean>

    suspend fun postSurveyForm(surveyForm: SurveyForm): Result<Unit>
}
