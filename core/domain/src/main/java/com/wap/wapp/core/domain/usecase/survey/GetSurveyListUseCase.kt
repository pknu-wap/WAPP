package com.wap.wapp.core.domain.usecase.survey

import com.wap.wapp.core.data.repository.survey.SurveyRepository
import com.wap.wapp.core.model.survey.Survey
import javax.inject.Inject

class GetSurveyListUseCase @Inject constructor(
    private val surveyRepository: SurveyRepository,
) {
    suspend operator fun invoke(): Result<List<Survey>> =
        surveyRepository.getSurveyList()
}
