package com.wap.wapp.core.domain.usecase.survey

import com.wap.wapp.core.data.repository.survey.SurveyFormRepository
import javax.inject.Inject

class GetSurveyFormUseCase @Inject constructor(
    private val surveyFormRepository: SurveyFormRepository,
) {
    suspend operator fun invoke(eventId: String) = surveyFormRepository.getSurveyForm(eventId)
}
