package com.wap.wapp.core.domain.usecase.survey

import com.wap.wapp.core.data.repository.survey.SurveyRepository
import javax.inject.Inject

class DeleteSurveyUseCase @Inject constructor(
    private val surveyRepository: SurveyRepository,
) {
    suspend operator fun invoke(surveyId: String): Result<Unit> =
        surveyRepository.deleteSurvey(surveyId)
}
