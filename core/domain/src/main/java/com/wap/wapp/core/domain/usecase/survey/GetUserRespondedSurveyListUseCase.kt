package com.wap.wapp.core.domain.usecase.survey

import com.wap.wapp.core.data.repository.survey.SurveyRepository
import com.wap.wapp.core.model.survey.Survey
import javax.inject.Inject

class GetUserRespondedSurveyListUseCase @Inject constructor(
    private val surveyRepository: SurveyRepository,
) {
    suspend operator fun invoke(userId: String): Result<List<Survey>> =
        surveyRepository.getUserRespondedSurveyList(userId)
}
