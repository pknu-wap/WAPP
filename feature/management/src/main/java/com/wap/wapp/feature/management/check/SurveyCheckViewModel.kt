package com.wap.wapp.feature.management.check

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wap.wapp.core.domain.usecase.survey.GetSurveyUseCase
import com.wap.wapp.core.model.survey.Survey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SurveyCheckViewModel @Inject constructor(
    private val getSurveyUseCase: GetSurveyUseCase,
) : ViewModel() {
    private val _surveyCheckUiEvent: MutableSharedFlow<SurveyUiEvent> = MutableSharedFlow()
    val surveyCheckUiEvent: SharedFlow<SurveyUiEvent> = _surveyCheckUiEvent.asSharedFlow()

    private val _surveyUiState: MutableStateFlow<SurveyUiState> =
        MutableStateFlow(SurveyUiState.Init)
    val surveyUiState: StateFlow<SurveyUiState> = _surveyUiState.asStateFlow()

    fun getSurvey(surveyId: String) {
        viewModelScope.launch {
            getSurveyUseCase(surveyId)
                .onSuccess { survey ->
                    _surveyUiState.value = SurveyUiState.Success(survey)
                }
                .onFailure { throwable ->
                    _surveyCheckUiEvent.emit(SurveyUiEvent.Failure(throwable))
                }
        }
    }

    sealed class SurveyUiState {
        data object Init : SurveyUiState()

        data class Success(val survey: Survey) : SurveyUiState()
    }

    sealed class SurveyUiEvent {
        data class Failure(val throwable: Throwable) : SurveyUiEvent()
    }
}
