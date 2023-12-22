package com.wap.wapp.core.domain.usecase.survey

import com.wap.wapp.core.data.repository.survey.SurveyRepository
import javax.inject.Inject

class GetSurveyFormUseCase @Inject constructor(
    private val surveyRepository: SurveyRepository,
) {
    suspend operator fun invoke(eventId: Int) = surveyRepository.getSurveyForm(eventId)
}
