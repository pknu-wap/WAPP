package com.wap.wapp.core.domain.usecase.event

import com.wap.wapp.core.data.repository.event.EventRepository
import com.wap.wapp.core.data.repository.survey.SurveyFormRepository
import com.wap.wapp.core.data.repository.survey.SurveyRepository
import javax.inject.Inject

class DeleteEventUseCase @Inject constructor(
    private val eventRepository: EventRepository,
    private val surveyFormRepository: SurveyFormRepository,
    private val surveyRepository: SurveyRepository,
) {
    // 일정을 삭제합니다. 추가로 이와 관련된 설문 폼, 작성된 설문들을 제거합니다.
    suspend operator fun invoke(eventId: String): Result<Unit> = runCatching {
        eventRepository.deleteEvent(eventId).getOrThrow()

        surveyFormRepository.getSurveyFormListByEventId(eventId).map { surveyFormList ->
            surveyFormList.map { surveyForm ->
                surveyFormRepository.deleteSurveyForm(surveyForm.surveyFormId)
            }
        }.getOrThrow()

        surveyRepository.getSurveyListByEventId(eventId).map { surveyList ->
            surveyList.map { survey ->
                surveyRepository.deleteSurvey(survey.surveyId)
            }
        }.getOrThrow()
    }
}
