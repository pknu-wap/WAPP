package com.wap.wapp.feature.survey.answer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wap.wapp.core.domain.usecase.survey.GetSurveyFormUseCase
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

    private val _surveyAnswerEvent: MutableSharedFlow<SurveyAnswerUiEvent> = MutableSharedFlow()
    val surveyAnswerEvent = _surveyAnswerEvent.asSharedFlow()

    fun getSurveyForm(eventId: Int) {
        viewModelScope.launch {
            getSurveyFormUseCase(eventId = eventId)
                .onSuccess { surveyForm ->
                    _surveyFormUiState.value = SurveyFormUiState.Success(surveyForm)
                }
                .onFailure { throwable ->
                    _surveyAnswerEvent.emit(SurveyAnswerUiEvent.Failure(throwable))
                }
        }
    }

    sealed class SurveyFormUiState {
        data object Init : SurveyFormUiState()
        data class Success(val surveyForm: SurveyForm) : SurveyFormUiState()
    }

    sealed class SurveyAnswerUiEvent {
        data class Failure(val throwable: Throwable) : SurveyAnswerUiEvent()
    }
}
