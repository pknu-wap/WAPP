package com.wap.wapp.core.data.repository.survey

import com.wap.wapp.core.data.utils.toISOLocalDateTimeString
import com.wap.wapp.core.model.survey.SurveyForm
import com.wap.wapp.core.network.model.survey.form.SurveyFormRequest
import com.wap.wapp.core.network.source.survey.SurveyFormDataSource
import javax.inject.Inject

class SurveyFormRepositoryImpl @Inject constructor(
    private val surveyFormDataSource: SurveyFormDataSource,
) : SurveyFormRepository {
    override suspend fun getSurveyForm(eventId: String): Result<SurveyForm> =
        surveyFormDataSource.getSurveyForm(eventId).mapCatching { surveyFormResponse ->
            surveyFormResponse.toDomain()
        }

    override suspend fun getSurveyFormList(): Result<List<SurveyForm>> =
        surveyFormDataSource.getSurveyFormList().mapCatching { surveyFormResponseList ->
            surveyFormResponseList.map { surveyFormResponse ->
                surveyFormResponse.toDomain()
            }
        }

    override suspend fun postSurveyForm(surveyForm: SurveyForm): Result<Unit> =
        surveyFormDataSource.postSurveyForm(
            surveyFormRequest = SurveyFormRequest(
                eventId = surveyForm.eventId,
                title = surveyForm.title,
                content = surveyForm.content,
                surveyQuestionList = surveyForm.surveyQuestionList,
                deadline = surveyForm.deadline.toISOLocalDateTimeString(),
            ),
        )
}
