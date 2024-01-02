package com.wap.wapp.feature.survey

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wap.wapp.core.domain.usecase.survey.GetSurveyFormListUseCase
import com.wap.wapp.core.domain.usecase.survey.IsSubmittedSurveyUseCase
import com.wap.wapp.core.model.survey.SurveyForm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SurveyViewModel @Inject constructor(
    private val getSurveyFormListUseCase: GetSurveyFormListUseCase,
    private val isSubmittedSurveyUseCase: IsSubmittedSurveyUseCase,
) : ViewModel() {
    private val _surveyFormListUiState: MutableStateFlow<SurveyFormListUiState> =
        MutableStateFlow(SurveyFormListUiState.Init)
    val surveyFormListUiState = _surveyFormListUiState.asStateFlow()

    private val _surveyEvent: MutableSharedFlow<SurveyUiEvent> = MutableSharedFlow()
    val surveyEvent = _surveyEvent.asSharedFlow()

    init {
        getSurveyFormList()
    }

    private fun getSurveyFormList() {
        viewModelScope.launch {
            getSurveyFormListUseCase()
                .onSuccess { surveyFormList ->
                    _surveyFormListUiState.value = SurveyFormListUiState.Success(surveyFormList)
                }
                .onFailure { throwable ->
                    _surveyEvent.emit(SurveyUiEvent.Failure(throwable))
                }
        }
    }

    fun isSubmittedSurvey(eventId: String) {
        viewModelScope.launch {
            isSubmittedSurveyUseCase(eventId)
                .onSuccess { isSubmittedSurvey ->
                    if (isSubmittedSurvey) {
                        _surveyEvent.emit(SurveyUiEvent.AlreadySubmitted)
                    } else {
                        _surveyEvent.emit(SurveyUiEvent.NotSubmitted(eventId))
                    }
                }
                .onFailure { throwable ->
                    _surveyEvent.emit(SurveyUiEvent.Failure(throwable))
                }
        }
    }

    sealed class SurveyFormListUiState {
        data object Init : SurveyFormListUiState()
        data class Success(val surveyFormList: List<SurveyForm>) : SurveyFormListUiState()
    }

    sealed class SurveyUiEvent {
        data class Failure(val throwable: Throwable) : SurveyUiEvent()
        data object AlreadySubmitted : SurveyUiEvent()
        data class NotSubmitted(val eventId: String) : SurveyUiEvent()
    }
}
