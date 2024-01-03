package com.wap.wapp.core.domain.usecase.survey

import com.wap.wapp.core.data.repository.survey.SurveyRepository
import com.wap.wapp.core.data.repository.user.UserRepository
import com.wap.wapp.core.model.survey.SurveyAnswer
import java.time.LocalDateTime
import javax.inject.Inject

class PostSurveyUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val surveyRepository: SurveyRepository,
) {
    suspend operator fun invoke(
        eventId: String,
        title: String,
        content: String,
        surveyAnswerList: List<SurveyAnswer>,
    ): Result<Unit> = runCatching {
        val userId = userRepository.getUserId().getOrThrow()

        surveyRepository.postSurvey(
            userId = userId,
            eventId = eventId,
            title = title,
            content = content,
            surveyAnswerList = surveyAnswerList,
            surveyedAt = LocalDateTime.now(),
        )
    }
}
