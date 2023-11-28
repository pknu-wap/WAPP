package com.wap.wapp.feature.management.survey

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class SurveyRegistrationViewModel @Inject constructor() : ViewModel() {

    private val _surveyRegistrationEvent: MutableSharedFlow<SurveyRegistrationEvent> =
        MutableSharedFlow()
    val surveyRegistrationEvent = _surveyRegistrationEvent.asSharedFlow()

    private val _currentRegistrationState: MutableStateFlow<SurveyRegistrationState> =
        MutableStateFlow(SurveyRegistrationState.INFORMATION)
    val currentRegistrationState = _currentRegistrationState.asStateFlow()

    private val _surveyTitle: MutableStateFlow<String> = MutableStateFlow("")
    val surveyTitle = _surveyTitle.asStateFlow()

    private val _surveyContent: MutableStateFlow<String> = MutableStateFlow("")
    val surveyContent = _surveyContent.asStateFlow()

    private val _surveyQuestion: MutableStateFlow<String> = MutableStateFlow("")
    val surveyQuestion = _surveyQuestion.asStateFlow()

    private val _surveyQuestionList: MutableStateFlow<MutableList<SurveyQuestion>> =
        MutableStateFlow(mutableListOf())
    val surveyQuestionList = _surveyQuestionList.asStateFlow()

    private val _surveyTimeDeadline: MutableStateFlow<LocalTime> = MutableStateFlow(LOCAL_TIME_INIT)
    val surveyTimeDeadline = _surveyTimeDeadline.asStateFlow()

    private val _surveyDateDeadline: MutableStateFlow<LocalDate> = MutableStateFlow(LOCAL_DATE_INIT)
    val surveyDateDeadline = _surveyDateDeadline.asStateFlow()

    fun setSurveyRegistrationState(surveyRegistrationState: SurveyRegistrationState) {
        _currentRegistrationState.value = surveyRegistrationState
    }

    fun setSurveyTitle(title: String) {
        _surveyTitle.value = title
    }

    fun setSurveyContent(content: String) {
        _surveyContent.value = content
    }

    fun setSurveyQuestion(question: String) {
        _surveyQuestion.value = question
    }

    fun addSurveyQuestion(type: QuestionType) {
        if (_surveyQuestion.value.isNotEmpty()) {
            _surveyQuestionList.value.add(
                SurveyQuestion(
                    questionTitle = _surveyQuestion.value,
                    questionType = type,
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

    fun setSurveyTimeDeadline(time: LocalTime) {
        _surveyTimeDeadline.value = time
    }

    fun setSurveyDateDeadline(date: LocalDate) {
        _surveyDateDeadline.value = date
    }

    private fun clearSurveyQuestionState() {
        _surveyQuestion.value = EMPTY
    }

    fun isValidSurveyInformation(): Boolean =
        _surveyContent.value.isNotEmpty() && _surveyTitle.value.isNotEmpty()

    fun isValidSurveyQuestion(): Boolean = _surveyQuestionList.value.isNotEmpty()

    fun isValidSurveyDeadline(): Boolean =
        _surveyDateDeadline.value != LOCAL_DATE_INIT && _surveyTimeDeadline.value != LOCAL_TIME_INIT

    fun registerSurvey() {}

    sealed class SurveyRegistrationEvent {
        data class ValidationError(val message: String) : SurveyRegistrationEvent()
        data class Failure(val error: Throwable) : SurveyRegistrationEvent()
    }

    companion object {
        const val EMPTY = ""
        val LOCAL_TIME_INIT: LocalTime = LocalTime.now()
        val LOCAL_DATE_INIT: LocalDate = LocalDate.now()
    }
}
