package com.wap.wapp.feature.survey.answer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wap.wapp.core.domain.usecase.event.GetEventUseCase
import com.wap.wapp.core.domain.usecase.survey.GetSurveyFormUseCase
import com.wap.wapp.core.domain.usecase.survey.PostSurveyUseCase
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
    private val getEventUseCase: GetEventUseCase,
    private val getSurveyFormUseCase: GetSurveyFormUseCase,
    private val postSurveyUseCase: PostSurveyUseCase,
) : ViewModel() {
    private val _surveyAnswerState: MutableStateFlow<SurveyAnswerState> =
        MutableStateFlow(SurveyAnswerState.SURVEY_OVERVIEW)
    val surveyAnswerState = _surveyAnswerState.asStateFlow()

    // 출력 SurveyForm State
    private val _surveyFormUiState: MutableStateFlow<SurveyFormUiState> =
        MutableStateFlow(SurveyFormUiState.Init)
    val surveyFormUiState = _surveyFormUiState.asStateFlow()

    // 사용자 입력 SurveyForm State
    private val _surveyForm: MutableStateFlow<SurveyForm> = MutableStateFlow(SurveyForm())

    private val _eventName: MutableStateFlow<String> = MutableStateFlow("")
    val eventName = _eventName.asStateFlow()

    private val _surveyAnswerEvent: MutableSharedFlow<SurveyAnswerUiEvent> = MutableSharedFlow()
    val surveyAnswerEvent = _surveyAnswerEvent.asSharedFlow()

    private val _questionNumber: MutableStateFlow<Int> = MutableStateFlow(0)
    val questionNumber = _questionNumber.asStateFlow()

    // 설문 응답 리스트
    private val _surveyAnswerList: MutableStateFlow<MutableList<SurveyAnswer>> =
        MutableStateFlow(mutableListOf())
    val surveyAnswerList = _surveyAnswerList.asStateFlow()

    private val _subjectiveAnswer: MutableStateFlow<String> = MutableStateFlow("")
    val subjectiveAnswer = _subjectiveAnswer.asStateFlow()

    private val _objectiveAnswer: MutableStateFlow<Rating> = MutableStateFlow(Rating.GOOD)
    val objectiveAnswer = _objectiveAnswer.asStateFlow()

    fun getSurveyForm(surveyFormId: String) {
        viewModelScope.launch {
            getSurveyFormUseCase(surveyFormId = surveyFormId)
                .onSuccess { surveyForm ->
                    _surveyFormUiState.value = SurveyFormUiState.Success(surveyForm)
                    _surveyForm.value = surveyForm
                    getEvent()
                }
                .onFailure { throwable ->
                    _surveyAnswerEvent.emit(SurveyAnswerUiEvent.Failure(throwable))
                }
        }
    }

    private fun getEvent() = viewModelScope.launch {
        getEventUseCase(eventId = _surveyForm.value.eventId)
            .onSuccess { event ->
                _eventName.value = event.title
            }
            .onFailure { throwable ->
                _surveyAnswerEvent.emit(SurveyAnswerUiEvent.Failure(throwable))
            }
    }

    fun addSurveyAnswer() {
        val questionNumber = _questionNumber.value
        val surveyQuestion = _surveyForm.value.surveyQuestionList[questionNumber]

        when (surveyQuestion.questionType) { // 새로운 질문에 답변을 작성하는 경우
            QuestionType.SUBJECTIVE -> {
                _surveyAnswerList.value.add( // 현재 질문 답변 리스트에 저장
                    SurveyAnswer(
                        questionType = surveyQuestion.questionType,
                        questionTitle = surveyQuestion.questionTitle,
                        questionAnswer = _subjectiveAnswer.value,
                    ),
                )
                clearSubjectiveAnswer() // 질문 상태 초기화
            }

            QuestionType.OBJECTIVE -> {
                _surveyAnswerList.value.add(
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

    fun editSurveyAnswer() {
        val questionNumber = _questionNumber.value
        val surveyQuestion = _surveyForm.value.surveyQuestionList[questionNumber]
        val surveyAnswerList = _surveyAnswerList.value

        when (surveyQuestion.questionType) { // 새로운 질문에 답변을 작성하는 경우
            QuestionType.SUBJECTIVE -> {
                surveyAnswerList[questionNumber] = SurveyAnswer(
                    questionType = surveyQuestion.questionType,
                    questionTitle = surveyQuestion.questionTitle,
                    questionAnswer = _subjectiveAnswer.value,
                )
                clearSubjectiveAnswer() // 질문 상태 초기화
            }

            QuestionType.OBJECTIVE -> {
                surveyAnswerList[questionNumber] = SurveyAnswer(
                    questionType = surveyQuestion.questionType,
                    questionTitle = surveyQuestion.questionTitle,
                    questionAnswer = _objectiveAnswer.value.toString(),
                )
                clearObjectiveAnswer()
            }
        }
    }

    fun submitSurvey() {
        val surveyForm = _surveyForm.value
        val surveyAnswerList = _surveyAnswerList.value

        viewModelScope.launch {
            postSurveyUseCase(
                surveyFormId = surveyForm.surveyFormId,
                eventId = surveyForm.eventId,
                title = surveyForm.title,
                content = surveyForm.content,
                surveyAnswerList = surveyAnswerList,
            ).onSuccess {
                _surveyAnswerEvent.emit(SurveyAnswerUiEvent.SubmitSuccess)
            }.onFailure { throwable ->
                _surveyAnswerEvent.emit(SurveyAnswerUiEvent.Failure(throwable))
            }
        }
    }

    fun setSubjectiveAnswer(answer: String) { _subjectiveAnswer.value = answer }

    fun setObjectiveAnswer(answer: Rating) { _objectiveAnswer.value = answer }

    fun setNextQuestionAndAnswer() {
        _questionNumber.value += 1
        if (_questionNumber.value < _surveyAnswerList.value.size) { // 다음 질문이 아미 작성된 질문인 경우
            setSurveyAnswer()
        }
    }

    fun setPreviousQuestionAndAnswer() {
        _questionNumber.value -= 1
        setSurveyAnswer()
    }

    private fun setSurveyAnswer() {
        val surveyAnswer = _surveyAnswerList.value[_questionNumber.value]
        when (surveyAnswer.questionType) {
            QuestionType.SUBJECTIVE -> {
                setSubjectiveAnswer(surveyAnswer.questionAnswer)
            }

            QuestionType.OBJECTIVE -> {
                when (surveyAnswer.questionAnswer) {
                    "GOOD" -> setObjectiveAnswer(Rating.GOOD)
                    "MEDIOCRE" -> setObjectiveAnswer(Rating.MEDIOCRE)
                    "BAD" -> setObjectiveAnswer(Rating.BAD)
                }
            }
        }
    }

    fun setSurveyAnswerState(state: SurveyAnswerState) { _surveyAnswerState.value = state }

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
