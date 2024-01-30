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

    private val _surveyEventSelection: MutableStateFlow<Event> =
        MutableStateFlow(EVENT_SELECTION_INIT)
    val surveyEventSelection = _surveyEventSelection.asStateFlow()

    private val _surveyTitle: MutableStateFlow<String> = MutableStateFlow("")
    val surveyTitle = _surveyTitle.asStateFlow()

    private val _surveyContent: MutableStateFlow<String> = MutableStateFlow("")
    val surveyContent = _surveyContent.asStateFlow()

    private val _surveyQuestionTitle: MutableStateFlow<String> = MutableStateFlow("")
    val surveyQuestionTitle = _surveyQuestionTitle.asStateFlow()

    private val _surveyQuestionType: MutableStateFlow<QuestionType> =
        MutableStateFlow(QuestionType.SUBJECTIVE)
    val surveyQuestionType = _surveyQuestionType.asStateFlow()

    private val _surveyQuestionNumber: MutableStateFlow<Int> = MutableStateFlow(0)
    val surveyQuestionNumber = _surveyQuestionNumber.asStateFlow()

    private val _surveyQuestionTotalNumber: MutableStateFlow<Int> = MutableStateFlow(0)
    val surveyQuestionTotalNumber = _surveyQuestionTotalNumber.asStateFlow()

    private val surveyQuestionList: MutableStateFlow<MutableList<SurveyQuestion>> =
        MutableStateFlow(mutableListOf())

    private val _surveyTimeDeadline: MutableStateFlow<LocalTime> =
        MutableStateFlow(DateUtil.generateNowTime())
    val surveyTimeDeadline = _surveyTimeDeadline.asStateFlow()

    private val _surveyDateDeadline: MutableStateFlow<LocalDate> =
        MutableStateFlow(DateUtil.generateNowDate())
    val surveyDateDeadline = _surveyDateDeadline.asStateFlow()

    fun getEventList() = viewModelScope.launch {
        getEventListUseCase().onSuccess { eventList ->
            _eventList.value = EventsState.Success(eventList)
        }.onFailure { throwable ->
            _surveyRegistrationEvent.emit(SurveyRegistrationEvent.Failure(throwable))
        }
    }

    fun registerSurvey() = viewModelScope.launch {
        registerSurveyUseCase(
            event = surveyEventSelection.value,
            title = _surveyTitle.value,
            content = _surveyContent.value,
            surveyQuestionList = surveyQuestionList.value,
            deadlineDate = _surveyDateDeadline.value,
            deadlineTime = _surveyTimeDeadline.value,
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

    fun setSurveyEventSelection(event: Event) { _surveyEventSelection.value = event }

    fun setSurveyTitle(title: String) { _surveyTitle.value = title }

    fun setSurveyContent(content: String) { _surveyContent.value = content }

    fun setSurveyQuestionTitle(title: String) { _surveyQuestionTitle.value = title }

    fun setSurveyQuestionType(type: QuestionType) { _surveyQuestionType.value = type }

    fun setNextQuestionNumber() { _surveyQuestionNumber.value += 1 }

    fun setPreviousQuestionNumber() { _surveyQuestionNumber.value -= 1 }

    fun setLastQuestionNumber() {
        _surveyQuestionTotalNumber.value += 1 // TODO
        _surveyQuestionNumber.value = surveyQuestionList.value.size
    }

    fun setQuestion() {
        val currentNumber = _surveyQuestionNumber.value
        val totalSize = surveyQuestionList.value.size

        if (currentNumber < totalSize) {
            val surveyQuestion = surveyQuestionList.value[_surveyQuestionNumber.value]
            setSurveyQuestionTitle(surveyQuestion.questionTitle)
            setSurveyQuestionType(surveyQuestion.questionType)
            return
        }

        clearSurveyQuestionState()
    }

    fun addSurveyQuestion() {
        surveyQuestionList.value.add(
            SurveyQuestion(
                questionTitle = _surveyQuestionTitle.value,
                questionType = _surveyQuestionType.value,
            ),
        )
        clearSurveyQuestionState()
    }

    fun editSurveyQuestion() {
        val questionNumber = _surveyQuestionNumber.value
        surveyQuestionList.value[questionNumber] = SurveyQuestion(
            questionTitle = _surveyQuestionTitle.value,
            questionType = _surveyQuestionType.value,
        )
        clearSurveyQuestionState()
    }

    fun setSurveyQuestionFromQuestionList() {
        // 작성된 질문 목록을 불러올 때, 마지막 질문은 UI에 노출
        val lastSurveyQuestion = surveyQuestionList.value.removeLast()
        setSurveyQuestionTitle(lastSurveyQuestion.questionTitle)
        setSurveyQuestionType(lastSurveyQuestion.questionType)
    }

    fun setSurveyTimeDeadline(time: LocalTime) { _surveyTimeDeadline.value = time }

    fun setSurveyDateDeadline(date: LocalDate) { _surveyDateDeadline.value = date }

    private fun clearSurveyQuestionState() { _surveyQuestionTitle.value = "" }

    private fun isNotValidSurveyQuestion() = _surveyQuestionTitle.value.isBlank()

    private fun isNotValidEventSelection() =
        _surveyEventSelection.value.eventId == EVENT_SELECTION_INIT.eventId

    private fun isNotValidInformation() =
        _surveyTitle.value.isBlank() || _surveyContent.value.isBlank()

    private fun isNotValidDeadline() = _surveyDateDeadline.value <= DateUtil.generateNowDate()

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
