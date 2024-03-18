package com.wap.wapp.feature.survey.check.detail

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
class SurveyDetailViewModel @Inject constructor(
    private val getSurveyUseCase: GetSurveyUseCase,
) : ViewModel() {
    private val _errorFlow: MutableSharedFlow<Throwable> = MutableSharedFlow()
    val errorFlow: SharedFlow<Throwable> = _errorFlow.asSharedFlow()

    private val _surveyUiState: MutableStateFlow<SurveyUiState> =
        MutableStateFlow(SurveyUiState.Loading)
    val surveyUiState: StateFlow<SurveyUiState> = _surveyUiState.asStateFlow()

    fun getSurvey(surveyId: String) {
        viewModelScope.launch {
            getSurveyUseCase(surveyId)
                .onSuccess { survey ->
                    _surveyUiState.value = SurveyUiState.Success(survey)
                }
                .onFailure { throwable ->
                    _errorFlow.emit(throwable)
                }
        }
    }

    sealed class SurveyUiState {
        data object Loading : SurveyUiState()
        data class Success(val survey: Survey) : SurveyUiState()
    }
}
