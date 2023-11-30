package com.wap.wapp.feature.management.survey

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wap.wapp.core.domain.usecase.management.RegisterSurveyUseCase
import com.wap.wapp.core.model.event.Event
import com.wap.wapp.core.model.survey.QuestionType
import com.wap.wapp.core.model.survey.SurveyQuestion
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SurveyRegistrationViewModel @Inject constructor(
    private val registerSurveyUseCase: RegisterSurveyUseCase,
) : ViewModel() {
    private val _surveyRegistrationEvent: MutableSharedFlow<SurveyRegistrationEvent> =
        MutableSharedFlow()
    val surveyRegistrationEvent = _surveyRegistrationEvent.asSharedFlow()

    private val _currentRegistrationState: MutableStateFlow<SurveyRegistrationState> =
        MutableStateFlow(SurveyRegistrationState.EVENT_SELECTION)
    val currentRegistrationState = _currentRegistrationState.asStateFlow()

    private val _eventList: MutableStateFlow<List<Event>> = MutableStateFlow(emptyList())
    val eventList = _eventList.asStateFlow()

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
        MutableStateFlow(QuestionType.ESSAY)
    val surveyQuestionType = _surveyQuestionType.asStateFlow()

    private val _surveyQuestionList: MutableStateFlow<MutableList<SurveyQuestion>> =
        MutableStateFlow(mutableListOf())
    val surveyQuestionList = _surveyQuestionList.asStateFlow()

    private val _surveyTimeDeadline: MutableStateFlow<LocalTime> = MutableStateFlow(LOCAL_TIME_INIT)
    val surveyTimeDeadline = _surveyTimeDeadline.asStateFlow()

    private val _surveyDateDeadline: MutableStateFlow<LocalDate> = MutableStateFlow(LOCAL_DATE_INIT)
    val surveyDateDeadline = _surveyDateDeadline.asStateFlow()

    // Content 전환 함수, 전환 전 내용 검증
    fun setSurveyRegistrationState(surveyRegistrationState: SurveyRegistrationState) {
        when (surveyRegistrationState) {
            SurveyRegistrationState.EVENT_SELECTION -> { /* initial value */ }

            SurveyRegistrationState.INFORMATION -> {
                if (isNotValidEventSelection()) {
                    emitValidationErrorMessage("행사를 선택해 주세요.")
                    return
                }
            }

            SurveyRegistrationState.QUESTION -> {
                if (isNotValidInformation()) {
                    emitValidationErrorMessage("제목과 내용을 확인해 주세요.")
                    return
                }
            }

            SurveyRegistrationState.DEADLINE -> {
                if (isValidSurveyQuestion()) {
                    addSurveyQuestion() // 현재 작성한 질문, 질문 리스트에 삽입
                } else {
                    emitValidationErrorMessage("질문 내용을 확인해 주세요.")
                    return
                }
            }
        }
        _currentRegistrationState.value = surveyRegistrationState
    }

    fun getEventList() {
        viewModelScope.launch {
            // TODO GET EVENT LIST
            val eventList = emptyList<Event>()
            _eventList.value = listOf(
                Event("ㅂㅈㄷ", 1, "", LocalDate.now(), "안녕"),
                Event("123", 2, "", LocalDate.now(), "누구세요"),
            )
        }
    }

    fun registerSurvey() {
        viewModelScope.launch {
            if (isValidDeadline()) {
                registerSurveyUseCase(
                    event = surveyEventSelection.value,
                    title = _surveyTitle.value,
                    content = _surveyContent.value,
                    surveyQuestion = _surveyQuestionList.value,
                    deadlineDate = _surveyDateDeadline.value,
                    deadlineTime = _surveyTimeDeadline.value,
                ).onSuccess {
                    _surveyRegistrationEvent.emit(SurveyRegistrationEvent.Success)
                }.onFailure { throwable ->
                    _surveyRegistrationEvent.emit(SurveyRegistrationEvent.Failure(throwable))
                }
            } else {
                _surveyRegistrationEvent.emit(
                    SurveyRegistrationEvent.ValidationError(
                        "최소 하루 이상 설문 날짜를 지정하세요.",
                    ),
                )
            }
        }
    }

    fun setSurveyEventSelection(event: Event) { _surveyEventSelection.value = event }

    fun setSurveyTitle(title: String) { _surveyTitle.value = title }

    fun setSurveyContent(content: String) { _surveyContent.value = content }

    fun setSurveyQuestion(question: String) { _surveyQuestion.value = question }

    fun setSurveyQuestionType(questionType: QuestionType) {
        _surveyQuestionType.value = questionType
    }

    fun setSurveyTimeDeadline(time: LocalTime) { _surveyTimeDeadline.value = time }

    fun setSurveyDateDeadline(date: LocalDate) { _surveyDateDeadline.value = date }

    fun addSurveyQuestion() {
        if (isValidSurveyQuestion()) {
            _surveyQuestionList.value.add(
                SurveyQuestion(
                    questionTitle = _surveyQuestion.value,
                    questionType = _surveyQuestionType.value,
                ),
            )
            clearSurveyQuestionState()
        } else {
            viewModelScope.launch {
                _surveyRegistrationEvent.emit(
                    SurveyRegistrationEvent.ValidationError("질문 내용을 확인해주세요."),
                )
            }
        }
    }

    private fun clearSurveyQuestionState() { _surveyQuestion.value = EMPTY }

    private fun isValidSurveyQuestion() = _surveyQuestion.value.isNotBlank()
    private fun isNotValidEventSelection() =
        _surveyEventSelection.value.eventId == EVENT_SELECTION_INIT.eventId

    private fun isNotValidInformation() =
        _surveyTitle.value.isBlank() || _surveyContent.value.isBlank()

    private fun isValidDeadline() = _surveyDateDeadline.value > LocalDate.now()

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

    companion object {
        const val EMPTY = ""
        val LOCAL_TIME_INIT: LocalTime = LocalTime.now()
        val LOCAL_DATE_INIT: LocalDate = LocalDate.now()
        val EVENT_SELECTION_INIT: Event = Event("", -1, "", LocalDate.now(), "")
    }
}
