package com.wap.wapp.core.domain.usecase.management

import com.wap.wapp.core.data.repository.management.ManagementRepository
import com.wap.wapp.core.data.repository.user.UserRepository
import com.wap.wapp.core.model.event.Event
import com.wap.wapp.core.model.survey.SurveyForm
import com.wap.wapp.core.model.survey.SurveyQuestion
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

class RegisterSurveyUseCase @Inject constructor(
    private val managementRepository: ManagementRepository,
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(
        event: Event,
        title: String,
        content: String,
        surveyQuestion: List<SurveyQuestion>,
        deadlineDate: LocalDate,
        deadlineTime: LocalTime,
    ): Result<Unit> {
        return runCatching {
            val userId = userRepository.getUserId().getOrThrow()

            managementRepository.postSurveyForm(
                SurveyForm(
                    eventId = event.eventId,
                    userId = userId,
                    title = title,
                    content = content,
                    surveyQuestion = surveyQuestion,
                    deadline = LocalDateTime.of(deadlineDate, deadlineTime),
                ),
            )
        }
    }
}
