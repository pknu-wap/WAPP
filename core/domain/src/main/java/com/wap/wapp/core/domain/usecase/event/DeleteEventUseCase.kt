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
    suspend operator fun invoke(eventId: String): Result<Unit> = runCatching {
        eventRepository.deleteEvent(eventId)
        surveyFormRepository.getSurveyFormList(eventId).mapCatching { surveyFormList ->
            surveyFormList.map { surveyForm ->
                surveyFormRepository.deleteSurveyForm(surveyForm.surveyFormId)
            }
        }
        surveyRepository.getSurveyList(eventId).mapCatching { surveyList ->
            surveyList.map { survey ->
                surveyRepository.deleteSurvey(survey.surveyId)
            }
        }
    }
}
