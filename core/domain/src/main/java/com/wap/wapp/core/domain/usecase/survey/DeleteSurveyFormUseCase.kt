package com.wap.wapp.core.domain.usecase.survey

import com.wap.wapp.core.data.repository.survey.SurveyFormRepository
import com.wap.wapp.core.data.repository.survey.SurveyRepository
import javax.inject.Inject

class DeleteSurveyFormUseCase @Inject constructor(
    private val surveyFormRepository: SurveyFormRepository,
    private val surveyRepository: SurveyRepository,
) {
    // 설문 폼을 삭제합니다. 추가로 이와 관련된 작성된 설문들을 제거합니다.
    suspend operator fun invoke(surveyFormId: String): Result<Unit> = runCatching {
        surveyFormRepository.deleteSurveyForm(surveyFormId).getOrThrow()
        surveyRepository.getSurveyListBySurveyFormId(surveyFormId).map { surveyList ->
            surveyList.map { survey ->
                surveyRepository.deleteSurvey(survey.surveyId)
            }
        }.getOrThrow()
    }
}
