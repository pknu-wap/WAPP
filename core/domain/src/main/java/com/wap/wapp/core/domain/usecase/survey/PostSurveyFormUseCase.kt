package com.wap.wapp.core.domain.usecase.survey

import com.wap.wapp.core.data.repository.survey.SurveyFormRepository
import com.wap.wapp.core.model.event.Event
import com.wap.wapp.core.model.survey.SurveyQuestion
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

class PostSurveyFormUseCase @Inject constructor(
    private val surveyFormRepository: SurveyFormRepository,
) {
    suspend operator fun invoke(
        event: Event,
        title: String,
        content: String,
        surveyQuestionList: List<SurveyQuestion>,
        deadlineDate: LocalDate,
        deadlineTime: LocalTime,
    ): Result<Unit> = runCatching {
        surveyFormRepository.postSurveyForm(
            eventId = event.eventId,
            title = title,
            content = content,
            surveyQuestionList = surveyQuestionList,
            deadline = LocalDateTime.of(deadlineDate, deadlineTime),
        )
    }
}
