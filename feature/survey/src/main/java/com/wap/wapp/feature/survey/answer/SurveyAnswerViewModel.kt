package com.wap.wapp.feature.survey.answer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wap.wapp.core.domain.usecase.survey.GetSurveyFormUseCase
import com.wap.wapp.core.model.survey.QuestionType
import com.wap.wapp.core.model.survey.Rating
import com.wap.wapp.core.model.survey.SurveyAnswer
import com.wap.wapp.core.model.survey.SurveyForm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SurveyAnswerViewModel @Inject constructor(
    private val getSurveyFormUseCase: GetSurveyFormUseCase,
) : ViewModel() {
    private val _surveyFormUiState: MutableStateFlow<SurveyFormUiState> =
        MutableStateFlow(SurveyFormUiState.Init)
    val surveyFormUiState = _surveyFormUiState.asStateFlow()

    private val _surveyForm: MutableStateFlow<SurveyForm> = MutableStateFlow(SurveyForm())

    private val _surveyAnswerEvent: MutableSharedFlow<SurveyAnswerUiEvent> = MutableSharedFlow()
    val surveyAnswerEvent = _surveyAnswerEvent.asSharedFlow()

    private val _questionNumber: MutableStateFlow<Int> = MutableStateFlow(0)
    val questionNumber = _questionNumber.asStateFlow()

    // 설문 응답 리스트
    private val surveyAnswerList: MutableStateFlow<MutableList<SurveyAnswer>> =
        MutableStateFlow(mutableListOf())

    private val _subjectiveAnswer: MutableStateFlow<String> = MutableStateFlow("")
    val subjectiveAnswer = _subjectiveAnswer.asStateFlow()

    private val _objectiveAnswer: MutableStateFlow<Rating> = MutableStateFlow(Rating.GOOD)
    val objectiveAnswer = _objectiveAnswer.asStateFlow()

    fun getSurveyForm(eventId: Int) {
        viewModelScope.launch {
            getSurveyFormUseCase(eventId = eventId)
                .onSuccess { surveyForm ->
                    _surveyFormUiState.value = SurveyFormUiState.Success(surveyForm)
                    _surveyForm.value = surveyForm
                }
                .onFailure { throwable ->
                    _surveyAnswerEvent.emit(SurveyAnswerUiEvent.Failure(throwable))
                }
        }
    }

    fun submitSurvey() {
        viewModelScope.launch {
        }
    }

    fun setSurveyAnswer() {
        val questionNumber = _questionNumber.value
        val surveyQuestion = _surveyForm.value.surveyQuestionList[questionNumber]

        when (surveyQuestion.questionType) {
            QuestionType.SUBJECTIVE -> {
                surveyAnswerList.value.add( // 현재 질문 답변 리스트에 저장
                    SurveyAnswer(
                        questionType = surveyQuestion.questionType,
                        questionTitle = surveyQuestion.questionTitle,
                        questionAnswer = _subjectiveAnswer.value,
                    ),
                )
                clearSubjectiveAnswer() // 질문 상태 초기화
            }

            QuestionType.OBJECTIVE -> {
                surveyAnswerList.value.add(
                    SurveyAnswer(
                        questionType = surveyQuestion.questionType,
                        questionTitle = surveyQuestion.questionTitle,
                        questionAnswer = _objectiveAnswer.value.toString(),
                    ),
                )
                clearObjectiveAnswer()
            }
        }
    }

    fun setSubjectiveAnswer(answer: String) { _subjectiveAnswer.value = answer }

    fun setObjectiveAnswer(answer: Rating) { _objectiveAnswer.value = answer }

    fun setNextQuestionNumber() { _questionNumber.value += 1 }

    private fun clearSubjectiveAnswer() { _subjectiveAnswer.value = "" }

    private fun clearObjectiveAnswer() { _objectiveAnswer.value = Rating.GOOD }

    sealed class SurveyFormUiState {
        data object Init : SurveyFormUiState()
        data class Success(val surveyForm: SurveyForm) : SurveyFormUiState()
    }

    sealed class SurveyAnswerUiEvent {
        data class Failure(val throwable: Throwable) : SurveyAnswerUiEvent()
        data object SubmitSuccess : SurveyAnswerUiEvent()
    }
}
