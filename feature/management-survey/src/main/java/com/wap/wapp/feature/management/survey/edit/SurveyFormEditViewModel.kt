package com.wap.wapp.feature.management.survey.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wap.wapp.core.commmon.util.DateUtil
import com.wap.wapp.core.domain.usecase.event.GetEventListUseCase
import com.wap.wapp.core.domain.usecase.survey.DeleteSurveyFormAndRelatedSurveysUseCase
import com.wap.wapp.core.domain.usecase.survey.GetSurveyFormUseCase
import com.wap.wapp.core.domain.usecase.survey.UpdateSurveyFormUseCase
import com.wap.wapp.core.model.event.Event
import com.wap.wapp.core.model.survey.QuestionType
import com.wap.wapp.core.model.survey.SurveyForm
import com.wap.wapp.core.model.survey.SurveyQuestion
import com.wap.wapp.feature.management.survey.EventsState
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
class SurveyFormEditViewModel @Inject constructor(
    private val getSurveyFormUseCase: GetSurveyFormUseCase,
    private val getEventListUseCase: GetEventListUseCase,
    private val updateSurveyFormUseCase: UpdateSurveyFormUseCase,
    private val deleteSurveyFormAndRelatedSurveysUseCase: DeleteSurveyFormAndRelatedSurveysUseCase,
) : ViewModel() {
    private val _surveyFormEditUiEvent: MutableSharedFlow<SurveyFormEditUiEvent> =
        MutableSharedFlow()
    val surveyFormEditUiEvent = _surveyFormEditUiEvent.asSharedFlow()

    private val _currentSurveyFormState: MutableStateFlow<SurveyFormState> =
        MutableStateFlow(SurveyFormState.EVENT_SELECTION)
    val currentSurveyFormState = _currentSurveyFormState.asStateFlow()

    // 불러올 설문 형식 Id
    private val surveyFormId: MutableStateFlow<String> = MutableStateFlow("")

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
        setSurveyQuestionList(surveyForm.surveyQuestionList)
        setSurveyQuestionFromQuestionList()
        setSurveyContent(surveyForm.content)
        setSurveyTimeDeadline(surveyForm.deadline.toLocalTime())
        setSurveyDateDeadline(surveyForm.deadline.toLocalDate())
        _currentQuestionNumber.value = _questionList.value.lastIndex
        _totalQuestionNumber.value = _questionList.value.lastIndex
    }

    fun updateSurveyForm() = viewModelScope.launch {
        val surveyForm = SurveyForm(
            surveyFormId = surveyFormId.value,
            eventId = _eventSelection.value.eventId,
            title = _surveyTitle.value,
            content = _surveyContent.value,
            surveyQuestionList = _questionList.value,
            deadline = LocalDateTime.of(_dateDeadline.value, _timeDeadline.value),
        )

        updateSurveyFormUseCase(surveyForm = surveyForm)
            .onSuccess {
                _surveyFormEditUiEvent.emit(SurveyFormEditUiEvent.EditSuccess)
            }.onFailure { throwable ->
                _surveyFormEditUiEvent.emit(SurveyFormEditUiEvent.Failure(throwable))
            }
    }

    fun deleteSurveyForm() = viewModelScope.launch {
        deleteSurveyFormAndRelatedSurveysUseCase(surveyFormId.value)
            .onSuccess {
                _surveyFormEditUiEvent.emit(SurveyFormEditUiEvent.EditSuccess)
            }.onFailure { throwable ->
                _surveyFormEditUiEvent.emit(SurveyFormEditUiEvent.Failure(throwable))
            }
    }

    fun getEventList() = viewModelScope.launch {
        getEventListUseCase().onSuccess { eventList ->
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

    private fun setSurveyFormId(surveyFormId: String) { this.surveyFormId.value = surveyFormId }

    fun setSurveyEventSelection(event: Event) { _eventSelection.value = event }

    fun setSurveyTitle(title: String) { _surveyTitle.value = title }

    fun setSurveyContent(content: String) { _surveyContent.value = content }

    fun setSurveyQuestionTitle(question: String) { _questionTitle.value = question }

    fun setSurveyQuestionType(questionType: QuestionType) { _questionType.value = questionType }

    fun setNextQuestionNumber() { _currentQuestionNumber.value += 1 }

    fun setPreviousQuestionNumber() { _currentQuestionNumber.value -= 1 }

    fun setLastQuestion() {
        val lastIndex = _questionList.value.size
        _currentQuestionNumber.value = lastIndex
        _totalQuestionNumber.value = lastIndex
        clearSurveyQuestionTitle()
        setDefaultSurveyQuestionType()
    }

    // 현재 질문 번호에 맞는 질문
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

    // 이전 버튼 클릭시 진입점이 질문 페이지인 경우, 응답 목록에서 마지막 질문 노출
    private fun setSurveyQuestionList(surveyQuestionList: List<SurveyQuestion>) {
        _questionList.value.addAll(surveyQuestionList)
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

    fun deleteSurveyQuestion() {
        val totalQuestionNumber = _totalQuestionNumber.value
        if (totalQuestionNumber < 1) {
            emitValidationErrorMessage("삭제할 문항이 없습니다.")
            return
        }

        val currentQuestionNumber = _currentQuestionNumber.value
        val lastQuestionNumber = _questionList.value.lastIndex // 마지막 질문 번호
        if (currentQuestionNumber <= lastQuestionNumber) {
            val questionList = _questionList.value
            questionList.removeAt(currentQuestionNumber)
        } // 등록되지 않은 질문을 삭제하는 경우는 skip

        if (currentQuestionNumber > 0) { // 5/5 -> 4/4, 1/4 -> 1/3, 2/3 -> 1/2
            setPreviousQuestionNumber()
        }
        _totalQuestionNumber.value -= 1
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

    private fun setDefaultSurveyQuestionType() { _questionType.value = QuestionType.SUBJECTIVE }

    private fun clearSurveyQuestionTitle() { _questionTitle.value = "" }

    private fun isNotValidSurveyQuestion() = _questionTitle.value.isBlank()

    private fun isNotValidEventSelection() =
        _eventSelection.value.eventId == EVENT_SELECTION_INIT.eventId

    private fun isNotValidInformation() =
        _surveyTitle.value.isBlank() || _surveyContent.value.isBlank()

    private fun isNotValidDeadline() = _dateDeadline.value <= DateUtil.generateNowDate()

    private fun emitValidationErrorMessage(message: String) {
        viewModelScope.launch {
            _surveyFormEditUiEvent.emit(
                SurveyFormEditUiEvent.ValidationError(message),
            )
        }
    }

    sealed class SurveyFormEditUiEvent {
        data object EditSuccess : SurveyFormEditUiEvent()
        data object DeleteSuccess : SurveyFormEditUiEvent()
        data class Failure(val throwable: Throwable) : SurveyFormEditUiEvent()
        data class ValidationError(val message: String) : SurveyFormEditUiEvent()
    }

    companion object {
        val EVENT_SELECTION_INIT = Event("", "", "", "", LocalDateTime.MIN, LocalDateTime.MAX)
    }
}
