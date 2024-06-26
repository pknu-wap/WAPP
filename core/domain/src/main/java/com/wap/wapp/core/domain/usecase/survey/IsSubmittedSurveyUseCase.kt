package com.wap.wapp.core.domain.usecase.survey

import com.wap.wapp.core.data.repository.survey.SurveyRepository
import com.wap.wapp.core.data.repository.user.UserRepository
import javax.inject.Inject

class IsSubmittedSurveyUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val surveyRepository: SurveyRepository,
) {
    suspend operator fun invoke(surveyFormId: String): Result<Boolean> = runCatching {
        val userId = userRepository.getUserId().getOrThrow()

        surveyRepository.isSubmittedSurvey(
            userId = userId,
            surveyFormId = surveyFormId,
        ).getOrThrow()
    }
}
