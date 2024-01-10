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
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
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

    fun getEventList() = viewModelScope.launch {
        getEventListUseCase(DateUtil.generateNowDate())
            .onSuccess { eventList ->
                _eventList.value = eventList
            }.onFailure { throwable ->
                _surveyRegistrationEvent.emit(SurveyRegistrationEvent.Failure(throwable))
            }
    }

    fun registerSurvey() = viewModelScope.launch {
        registerSurveyUseCase(
            event = surveyEventSelection.value,
            title = _surveyTitle.value,
            content = _surveyContent.value,
            surveyQuestionList = _surveyQuestionList.value,
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

    fun setSurveyFormState(nextState: SurveyFormState) {
        _currentSurveyFormState.value = nextState
    }

    fun setSurveyEventSelection(event: Event) { _surveyEventSelection.value = event }

    fun setSurveyTitle(title: String) { _surveyTitle.value = title }

    fun setSurveyContent(content: String) { _surveyContent.value = content }

    fun setSurveyQuestion(question: String) { _surveyQuestion.value = question }

    fun setSurveyQuestionType(type: QuestionType) { _surveyQuestionType.value = type }

    fun setSurveyTimeDeadline(time: LocalTime) { _surveyTimeDeadline.value = time }

    fun setSurveyDateDeadline(date: LocalDate) { _surveyDateDeadline.value = date }

    fun addSurveyQuestion() {
        _surveyQuestionList.value.add(
            SurveyQuestion(
                questionTitle = _surveyQuestion.value,
                questionType = _surveyQuestionType.value,
            ),
        )
        clearSurveyQuestionState()
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
        val EVENT_SELECTION_INIT: Event =
            Event("", "", "", "", DateUtil.generateNowDateTime(), DateUtil.generateNowDateTime())
    }
}
