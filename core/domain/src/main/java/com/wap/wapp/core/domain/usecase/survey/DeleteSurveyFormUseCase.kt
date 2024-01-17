package com.wap.wapp.core.domain.usecase.survey

import com.wap.wapp.core.data.repository.survey.SurveyFormRepository
import javax.inject.Inject

class DeleteSurveyFormUseCase @Inject constructor(
    private val surveyFormRepository: SurveyFormRepository,
) {
    suspend operator fun invoke(surveyFormId: String) =
        surveyFormRepository.deleteSurveyForm(surveyFormId)
}