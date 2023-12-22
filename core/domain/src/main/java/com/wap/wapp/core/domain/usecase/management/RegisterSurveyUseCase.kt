package com.wap.wapp.core.domain.usecase.management

import com.wap.wapp.core.data.repository.survey.SurveyFormRepository
import com.wap.wapp.core.model.event.Event
import com.wap.wapp.core.model.survey.SurveyForm
import com.wap.wapp.core.model.survey.SurveyQuestion
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

class RegisterSurveyUseCase @Inject constructor(
    private val surveyFormRepository: SurveyFormRepository,
) {
    suspend operator fun invoke(
        event: Event,
        title: String,
        content: String,
        surveyQuestionList: List<SurveyQuestion>,
        deadlineDate: LocalDate,
        deadlineTime: LocalTime,
    ): Result<Unit> {
        return runCatching {
            surveyFormRepository.postSurveyForm(
                SurveyForm(
                    eventId = event.eventId,
                    title = title,
                    content = content,
                    surveyQuestionList = surveyQuestionList,
                    deadline = LocalDateTime.of(deadlineDate, deadlineTime),
                ),
            )
        }
    }
}
