package com.wap.wapp.core.network.source.management

import com.wap.wapp.core.network.model.management.SurveyFormRequest

interface ManagementDataSource {
    suspend fun getManager(userId: String): Result<Boolean>

    suspend fun postManager(userId: String): Result<Unit>

    suspend fun getManagementCode(code: String): Result<Boolean>

    suspend fun postSurveyForm(surveyFormRequest: SurveyFormRequest, eventId: Int): Result<Unit>
}
