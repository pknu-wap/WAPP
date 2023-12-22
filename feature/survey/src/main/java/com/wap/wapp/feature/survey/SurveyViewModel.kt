package com.wap.wapp.feature.survey

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wap.wapp.core.domain.usecase.survey.GetSurveyFormListUseCase
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

    sealed class SurveyFormListUiState {
        data object Init : SurveyFormListUiState()
        data class Success(val surveyFormList: List<SurveyForm>) : SurveyFormListUiState()
    }

    sealed class SurveyUiEvent {
        data class Failure(val throwable: Throwable) : SurveyUiEvent()
    }
}
