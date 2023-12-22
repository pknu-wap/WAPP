package com.wap.wapp.core.data.repository.survey

import com.wap.wapp.core.model.survey.SurveyForm
import com.wap.wapp.core.network.model.management.SurveyFormRequest
import com.wap.wapp.core.network.source.survey.SurveyFormDataSource
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class SurveyFormRepositoryImpl @Inject constructor(
    private val surveyFormDataSource: SurveyFormDataSource
): SurveyFormRepository {
    override suspend fun getSurveyForm(eventId: Int): Result<SurveyForm> {
        return surveyFormDataSource.getSurveyForm(eventId).mapCatching { surveyFormResponse ->
            surveyFormResponse.toDomain()
        }
    }

    override suspend fun getSurveyFormList(): Result<List<SurveyForm>> {
        return surveyFormDataSource.getSurveyFormList().mapCatching { surveyFormResponseList ->
            surveyFormResponseList.map { surveyFormResponse ->
                surveyFormResponse.toDomain()
            }
        }
    }

    override suspend fun postSurveyForm(surveyForm: SurveyForm): Result<Unit> {
        return surveyFormDataSource.postSurveyForm(
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
