package com.wap.wapp.core.data.repository.survey

import com.wap.wapp.core.data.utils.toISOLocalDateTimeString
import com.wap.wapp.core.model.survey.SurveyForm
import com.wap.wapp.core.model.survey.SurveyQuestion
import com.wap.wapp.core.network.model.survey.form.SurveyFormRequest
import com.wap.wapp.core.network.source.survey.SurveyFormDataSource
import java.time.LocalDateTime
import javax.inject.Inject

class SurveyFormRepositoryImpl @Inject constructor(
    private val surveyFormDataSource: SurveyFormDataSource,
) : SurveyFormRepository {
    override suspend fun getSurveyForm(surveyFormId: String): Result<SurveyForm> =
        surveyFormDataSource.getSurveyForm(surveyFormId).mapCatching { surveyFormResponse ->
            surveyFormResponse.toDomain()
        }

    override suspend fun getSurveyFormList(): Result<List<SurveyForm>> =
        surveyFormDataSource.getSurveyFormList().mapCatching { surveyFormResponseList ->
            surveyFormResponseList.map { surveyFormResponse ->
                surveyFormResponse.toDomain()
            }
        }

    override suspend fun deleteSurveyForm(surveyFormId: String): Result<Unit> =
        surveyFormDataSource.deleteSurveyForm(surveyFormId)

    override suspend fun getSurveyFormList(eventId: String): Result<List<SurveyForm>> =
        surveyFormDataSource.getSurveyFormList(eventId).mapCatching { surveyFormResponseList ->
            surveyFormResponseList.map { surveyFormResponse ->
                surveyFormResponse.toDomain()
            }
        }

    override suspend fun postSurveyForm(
        eventId: String,
        title: String,
        content: String,
        surveyQuestionList: List<SurveyQuestion>,
        deadline: LocalDateTime,
    ): Result<Unit> = surveyFormDataSource.postSurveyForm(
        eventId = eventId,
        title = title,
        content = content,
        surveyQuestionList = surveyQuestionList,
        deadline = deadline.toISOLocalDateTimeString(),
    )

    override suspend fun updateSurveyForm(surveyForm: SurveyForm): Result<Unit> =
        surveyFormDataSource.updateSurveyForm(
            surveyFormRequest = SurveyFormRequest(
                surveyFormId = surveyForm.surveyFormId,
                eventId = surveyForm.eventId,
                title = surveyForm.title,
                content = surveyForm.content,
                surveyQuestionList = surveyForm.surveyQuestionList,
                deadline = surveyForm.deadline.toISOLocalDateTimeString(),
            ),
        )
}
