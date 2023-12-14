package com.wap.wapp.core.data.repository.management

import com.wap.wapp.core.model.survey.SurveyForm
import com.wap.wapp.core.network.model.management.SurveyFormRequest
import com.wap.wapp.core.network.source.management.ManagementDataSource
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class ManagementRepositoryImpl @Inject constructor(
    private val managementDataSource: ManagementDataSource,
) : ManagementRepository {
    override suspend fun getManager(userId: String): Result<Boolean> {
        return managementDataSource.getManager(userId)
    }

    override suspend fun postManager(userId: String): Result<Unit> {
        return managementDataSource.postManager(userId)
    }

    override suspend fun getManagementCode(code: String): Result<Boolean> {
        return managementDataSource.getManagementCode(code)
    }

    override suspend fun postSurveyForm(surveyForm: SurveyForm): Result<Unit> {
        return managementDataSource.postSurveyForm(
            surveyFormRequest = SurveyFormRequest(
                eventId = surveyForm.eventId,
                title = surveyForm.title,
                content = surveyForm.content,
                surveyQuestionList = surveyForm.surveyQuestionList,
                deadline = surveyForm.deadline.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
            ),
            eventId = surveyForm.eventId,
        )
    }
}
