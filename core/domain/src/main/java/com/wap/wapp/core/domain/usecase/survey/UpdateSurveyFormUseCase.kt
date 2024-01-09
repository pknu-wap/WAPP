package com.wap.wapp.core.domain.usecase.survey

import com.wap.wapp.core.data.repository.survey.SurveyFormRepository
import com.wap.wapp.core.model.survey.SurveyForm
import javax.inject.Inject

class UpdateSurveyFormUseCase @Inject constructor(
    private val surveyFormRepository: SurveyFormRepository
){
    suspend operator fun invoke(surveyForm: SurveyForm): Result<Unit> = runCatching {
        surveyFormRepository.updateSurveyForm(surveyForm)
    }
}
