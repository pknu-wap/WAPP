package com.wap.wapp.core.data.repository.survey

import com.wap.wapp.core.model.survey.Survey
import com.wap.wapp.core.network.source.survey.SurveyDataSource
import javax.inject.Inject

class SurveyRepositoryImpl @Inject constructor(
    private val surveyDataSource: SurveyDataSource,
) : SurveyRepository {
    override suspend fun getSurveyList(): Result<List<Survey>> {
        return surveyDataSource.getSurveyList().mapCatching { surveyList ->
            surveyList.map { surveyResponse -> surveyResponse.toDomain() }
        }
    }
}
