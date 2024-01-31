package com.wap.wapp.feature.management.survey.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wap.wapp.core.commmon.util.DateUtil
import com.wap.wapp.core.domain.usecase.event.GetEventListUseCase
import com.wap.wapp.core.domain.usecase.survey.PostSurveyFormUseCase
import com.wap.wapp.core.model.event.Event
import com.wap.wapp.core.model.survey.QuestionType
import com.wap.wapp.core.model.survey.SurveyQuestion
import com.wap.wapp.feature.management.survey.SurveyFormState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class SurveyFormRegistrationViewModel @Inject constructor(
    private val registerSurveyUseCase: PostSurveyFormUseCase,
    private val getEventListUseCase: GetEventListUseCase,
) : ViewModel() {
    private val _surveyRegistrationEvent: MutableSharedFlow<SurveyRegistrationEvent> =
        MutableSharedFlow()
    val surveyRegistrationEvent = _surveyRegistrationEvent.asSharedFlow()

    private val _currentSurveyFormState: MutableStateFlow<SurveyFormState> =
        MutableStateFlow(SurveyFormState.EVENT_SELECTION)
    val currentSurveyFormState = _currentSurveyFormState.asStateFlow()

    private val _eventList: MutableStateFlow<EventsState> = MutableStateFlow(EventsState.Loading)
    val eventList: StateFlow<EventsState> = _eventList.asStateFlow()

    private val _eventSelection: MutableStateFlow<Event> =
        MutableStateFlow(EVENT_SELECTION_INIT)
    val eventSelection = _eventSelection.asStateFlow()

    private val _surveyTitle: MutableStateFlow<String> = MutableStateFlow("")
    val surveyTitle = _surveyTitle.asStateFlow()

    private val _surveyContent: MutableStateFlow<String> = MutableStateFlow("")
    val surveyContent = _surveyContent.asStateFlow()

    private val _questionTitle: MutableStateFlow<String> = MutableStateFlow("")
    val questionTitle = _questionTitle.asStateFlow()

    private val _questionType: MutableStateFlow<QuestionType> =
        MutableStateFlow(QuestionType.SUBJECTIVE)
    val questionType = _questionType.asStateFlow()

    private val _currentQuestionNumber: MutableStateFlow<Int> = MutableStateFlow(0)
    val currentQuestionNumber = _currentQuestionNumber.asStateFlow() // 현재 질문의 번호 UI State

    private val _totalQuestionNumber: MutableStateFlow<Int> = MutableStateFlow(0)
    val totalQuestionNumber = _totalQuestionNumber.asStateFlow() // 전체 질문의 번호 UI State

    private val _questionList: MutableStateFlow<MutableList<SurveyQuestion>> =
        MutableStateFlow(mutableListOf())
    val questionList = _questionList.asStateFlow() // 사용자가 작성한 질문 목록

    private val _timeDeadline: MutableStateFlow<LocalTime> =
        MutableStateFlow(DateUtil.generateNowTime())
    val timeDeadline = _timeDeadline.asStateFlow()

    private val _dateDeadline: MutableStateFlow<LocalDate> =
        MutableStateFlow(DateUtil.generateNowDate())
    val dateDeadline = _dateDeadline.asStateFlow()

    fun getEventList() = viewModelScope.launch {
        getEventListUseCase().onSuccess { eventList ->
            _eventList.value = EventsState.Success(eventList)
        }.onFailure { throwable ->
            _surveyRegistrationEvent.emit(SurveyRegistrationEvent.Failure(throwable))
        }
    }

    fun registerSurvey() = viewModelScope.launch {
        registerSurveyUseCase(
            event = _eventSelection.value,
            title = _surveyTitle.value,
            content = _surveyContent.value,
            surveyQuestionList = _questionList.value,
            deadlineDate = _dateDeadline.value,
            deadlineTime = _timeDeadline.value,
        ).onSuccess {
            _surveyRegistrationEvent.emit(SurveyRegistrationEvent.Success)
        }.onFailure { throwable ->
            _surveyRegistrationEvent.emit(SurveyRegistrationEvent.Failure(throwable))
        }
    }

    fun validateSurveyForm(currentState: SurveyFormState): Boolean {
        when (currentState) {
            SurveyFormState.EVENT_SELECTION -> {
                if (isNotValidEventSelection()) {
                    emitValidationErrorMessage("행사를 선택해 주세요.")
                    return false
                }
            }

            SurveyFormState.INFORMATION -> {
                if (isNotValidInformation()) {
                    emitValidationErrorMessage("제목과 내용을 확인해 주세요.")
                    return false
                }
            }

            SurveyFormState.QUESTION -> {
                if (isNotValidSurveyQuestion()) {
                    emitValidationErrorMessage("질문 내용을 확인해 주세요.")
                    return false
                }
            }

            SurveyFormState.DEADLINE -> {
                if (isNotValidDeadline()) {
                    emitValidationErrorMessage("최소 하루 이상 설문 날짜를 지정하세요.")
                    return false
                }
            }
        }
        return true
    }

    fun setSurveyFormState(nextState: SurveyFormState) { _currentSurveyFormState.value = nextState }

    fun setSurveyEventSelection(event: Event) { _eventSelection.value = event }

    fun setSurveyTitle(title: String) { _surveyTitle.value = title }

    fun setSurveyContent(content: String) { _surveyContent.value = content }

    fun setSurveyQuestionTitle(title: String) { _questionTitle.value = title }

    fun setSurveyQuestionType(type: QuestionType) { _questionType.value = type }

    fun setNextQuestionNumber() { _currentQuestionNumber.value += 1 }

    fun setPreviousQuestionNumber() { _currentQuestionNumber.value -= 1 }

    fun setLastQuestion() {
        _currentQuestionNumber.value = _questionList.value.size // 마지막 질문 번호로 변경
        _totalQuestionNumber.value += 1 // 전체 질문 갯수 추가
        clearSurveyQuestionTitle()
    }

    fun setQuestion() {
        val currentNumber = _currentQuestionNumber.value
        val totalSize = _questionList.value.size

        if (currentNumber < totalSize) {
            val surveyQuestion = _questionList.value[_currentQuestionNumber.value]
            setSurveyQuestionTitle(surveyQuestion.questionTitle)
            setSurveyQuestionType(surveyQuestion.questionType)
            return
        }
    }

    fun addSurveyQuestion() {
        _questionList.value.add(
            SurveyQuestion(
                questionTitle = _questionTitle.value,
                questionType = _questionType.value,
            ),
        )
        clearSurveyQuestionTitle()
    }

    fun editSurveyQuestion() {
        val questionNumber = _currentQuestionNumber.value
        _questionList.value[questionNumber] = SurveyQuestion(
            questionTitle = _questionTitle.value,
            questionType = _questionType.value,
        )
        clearSurveyQuestionTitle()
    }

    fun setSurveyQuestionFromQuestionList() {
        // 작성된 질문 목록을 불러올 때, 마지막 질문은 UI에 노출
        val lastSurveyQuestion = _questionList.value.last()
        setSurveyQuestionTitle(lastSurveyQuestion.questionTitle)
        setSurveyQuestionType(lastSurveyQuestion.questionType)
    }

    fun setSurveyTimeDeadline(time: LocalTime) { _timeDeadline.value = time }

    fun setSurveyDateDeadline(date: LocalDate) { _dateDeadline.value = date }

    private fun clearSurveyQuestionTitle() { _questionTitle.value = "" }

    private fun isNotValidSurveyQuestion() = _questionTitle.value.isBlank()

    private fun isNotValidEventSelection() =
        _eventSelection.value.eventId == EVENT_SELECTION_INIT.eventId

    private fun isNotValidInformation() =
        _surveyTitle.value.isBlank() || _surveyContent.value.isBlank()

    private fun isNotValidDeadline() = _dateDeadline.value <= DateUtil.generateNowDate()

    private fun emitValidationErrorMessage(message: String) {
        viewModelScope.launch {
            _surveyRegistrationEvent.emit(
                SurveyRegistrationEvent.ValidationError(message),
            )
        }
    }

    sealed class SurveyRegistrationEvent {
        data class ValidationError(val message: String) : SurveyRegistrationEvent()
        data class Failure(val error: Throwable) : SurveyRegistrationEvent()
        data object Success : SurveyRegistrationEvent()
    }

    sealed class EventsState {
        data object Loading : EventsState()
        data class Success(val events: List<Event>) : EventsState()
        data class Failure(val throwable: Throwable) : EventsState()
    }

    companion object {
        val EVENT_SELECTION_INIT = Event("", "", "", "", LocalDateTime.MIN, LocalDateTime.MAX)
    }
}
