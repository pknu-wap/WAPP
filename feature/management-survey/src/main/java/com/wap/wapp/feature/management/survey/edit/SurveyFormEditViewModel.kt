package com.wap.wapp.feature.management.survey.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wap.wapp.core.commmon.util.DateUtil
import com.wap.wapp.core.domain.usecase.event.GetEventListUseCase
import com.wap.wapp.core.domain.usecase.survey.GetSurveyFormUseCase
import com.wap.wapp.core.domain.usecase.survey.UpdateSurveyFormUseCase
import com.wap.wapp.core.model.event.Event
import com.wap.wapp.core.model.survey.QuestionType
import com.wap.wapp.core.model.survey.SurveyForm
import com.wap.wapp.core.model.survey.SurveyQuestion
import com.wap.wapp.feature.management.survey.SurveyFormState
import com.wap.wapp.feature.management.survey.registration.SurveyFormRegistrationViewModel.EventsState
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
class SurveyFormEditViewModel @Inject constructor(
    private val getSurveyFormUseCase: GetSurveyFormUseCase,
    private val getEventListUseCase: GetEventListUseCase,
    private val updateSurveyFormUseCase: UpdateSurveyFormUseCase,
) : ViewModel() {
    private val _surveyFormEditUiEvent: MutableSharedFlow<SurveyFormEditUiEvent> =
        MutableSharedFlow()
    val surveyFormEditUiEvent = _surveyFormEditUiEvent.asSharedFlow()

    private val _currentSurveyFormState: MutableStateFlow<SurveyFormState> =
        MutableStateFlow(SurveyFormState.EVENT_SELECTION)
    val currentSurveyFormState = _currentSurveyFormState.asStateFlow()

    private val _eventList: MutableStateFlow<EventsState> = MutableStateFlow(EventsState.Loading)
    val eventList: StateFlow<EventsState> = _eventList.asStateFlow()

    private val surveyFormId: MutableStateFlow<String> = MutableStateFlow("")

    private val _surveyEventSelection: MutableStateFlow<Event> =
        MutableStateFlow(EVENT_SELECTION_INIT)
    val surveyEventSelection = _surveyEventSelection.asStateFlow()

    private val _surveyTitle: MutableStateFlow<String> = MutableStateFlow("")
    val surveyTitle = _surveyTitle.asStateFlow()

    private val _surveyContent: MutableStateFlow<String> = MutableStateFlow("")
    val surveyContent = _surveyContent.asStateFlow()

    private val _surveyQuestion: MutableStateFlow<String> = MutableStateFlow("")
    val surveyQuestion = _surveyQuestion.asStateFlow()

    private val _surveyQuestionType: MutableStateFlow<QuestionType> =
        MutableStateFlow(QuestionType.SUBJECTIVE)
    val surveyQuestionType = _surveyQuestionType.asStateFlow()

    private val _surveyQuestionList: MutableStateFlow<MutableList<SurveyQuestion>> =
        MutableStateFlow(mutableListOf())
    val surveyQuestionList = _surveyQuestionList.asStateFlow()

    private val _surveyTimeDeadline: MutableStateFlow<LocalTime> =
        MutableStateFlow(DateUtil.generateNowTime())
    val surveyTimeDeadline = _surveyTimeDeadline.asStateFlow()

    private val _surveyDateDeadline: MutableStateFlow<LocalDate> =
        MutableStateFlow(DateUtil.generateNowDate())
    val surveyDateDeadline = _surveyDateDeadline.asStateFlow()

    fun getSurveyForm(surveyFormId: String) { // 설문 불러오기
        viewModelScope.launch {
            getSurveyFormUseCase(surveyFormId = surveyFormId)
                .onSuccess { surveyForm ->
                    setSurveyForm(surveyForm)
                }
                .onFailure { throwable ->
                    _surveyFormEditUiEvent.emit(SurveyFormEditUiEvent.Failure(throwable))
                }
        }
    }

    private fun setSurveyForm(surveyForm: SurveyForm) { // 이전 설문 데이터, 상태 대입
        setSurveyFormId(surveyForm.surveyFormId)
        setSurveyEventSelection(EVENT_SELECTION_INIT.copy(eventId = surveyForm.eventId))
        setSurveyTitle(surveyForm.title)
        setSurveyQuestionList(surveyForm.surveyQuestionList.toMutableList())
        setSurveyContent(surveyForm.content)
        setSurveyTimeDeadline(surveyForm.deadline.toLocalTime())
        setSurveyDateDeadline(surveyForm.deadline.toLocalDate())
    }

    fun updateSurveyForm() = viewModelScope.launch {
        val surveyForm = SurveyForm(
            surveyFormId = surveyFormId.value,
            eventId = _surveyEventSelection.value.eventId,
            title = _surveyTitle.value,
            content = _surveyContent.value,
            surveyQuestionList = _surveyQuestionList.value,
            deadline = LocalDateTime.of(_surveyDateDeadline.value, _surveyTimeDeadline.value),
        )

        updateSurveyFormUseCase(surveyForm = surveyForm)
            .onSuccess {
                _surveyFormEditUiEvent.emit(SurveyFormEditUiEvent.Success)
            }.onFailure { throwable ->
                _surveyFormEditUiEvent.emit(SurveyFormEditUiEvent.Failure(throwable))
            }
    }

    fun getEventList() = viewModelScope.launch {
        getEventListUseCase(DateUtil.generateNowDate())
            .onSuccess { eventList ->
                _eventList.value = EventsState.Success(eventList)
            }.onFailure { throwable ->
                _surveyFormEditUiEvent.emit(SurveyFormEditUiEvent.Failure(throwable))
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

    fun addSurveyQuestion() {
        _surveyQuestionList.value.add(
            SurveyQuestion(
                questionTitle = _surveyQuestion.value,
                questionType = _surveyQuestionType.value,
            ),
        )
        clearSurveyQuestionState()
    }

    private fun setSurveyFormId(surveyFormId: String) { this.surveyFormId.value = surveyFormId }

    fun setSurveyEventSelection(event: Event) { _surveyEventSelection.value = event }

    fun setSurveyTitle(title: String) { _surveyTitle.value = title }

    fun setSurveyContent(content: String) { _surveyContent.value = content }

    fun setSurveyQuestion(question: String) { _surveyQuestion.value = question }

    fun setSurveyQuestionType(questionType: QuestionType) {
        _surveyQuestionType.value = questionType
    }

    fun setSurveyTimeDeadline(time: LocalTime) { _surveyTimeDeadline.value = time }

    fun setSurveyDateDeadline(date: LocalDate) { _surveyDateDeadline.value = date }

    private fun setSurveyQuestionList(surveyQuestionList: MutableList<SurveyQuestion>) {
        // 마지막 질문은 UI에 노출
        val lastSurveyQuestion = surveyQuestionList.removeLast()
        setSurveyQuestion(lastSurveyQuestion.questionTitle)
        setSurveyQuestionType(lastSurveyQuestion.questionType)

        _surveyQuestionList.value.addAll(surveyQuestionList)
    }

    private fun clearSurveyQuestionState() { _surveyQuestion.value = EMPTY }

    private fun isNotValidSurveyQuestion() = _surveyQuestion.value.isBlank()

    private fun isNotValidEventSelection() =
        _surveyEventSelection.value.eventId == EVENT_SELECTION_INIT.eventId

    private fun isNotValidInformation() =
        _surveyTitle.value.isBlank() || _surveyContent.value.isBlank()

    private fun isNotValidDeadline() = _surveyDateDeadline.value <= DateUtil.generateNowDate()

    private fun emitValidationErrorMessage(message: String) {
        viewModelScope.launch {
            _surveyFormEditUiEvent.emit(
                SurveyFormEditUiEvent.ValidationError(message),
            )
        }
    }

    sealed class SurveyFormEditUiEvent {
        data object Success : SurveyFormEditUiEvent()
        data class Failure(val throwable: Throwable) : SurveyFormEditUiEvent()
        data class ValidationError(val message: String) : SurveyFormEditUiEvent()
    }

    companion object {
        const val EMPTY = ""
        val EVENT_SELECTION_INIT: Event =
            Event("", "", "", "", DateUtil.generateNowDateTime(), DateUtil.generateNowDateTime())
    }
}
