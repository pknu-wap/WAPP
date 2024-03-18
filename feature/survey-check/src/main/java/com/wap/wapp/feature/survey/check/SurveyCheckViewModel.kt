package com.wap.wapp.feature.survey.check

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wap.wapp.core.domain.usecase.survey.GetSurveyListUseCase
import com.wap.wapp.core.model.survey.Survey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SurveyCheckViewModel @Inject constructor(
    private val getSurveyListUseCase: GetSurveyListUseCase,
) : ViewModel() {
    private val _surveyListUiState: MutableStateFlow<SurveyListUiState> =
        MutableStateFlow(SurveyListUiState.Init)
    val surveyListUiState = _surveyListUiState.asStateFlow()

    private val _errorFlow: MutableSharedFlow<Throwable> = MutableSharedFlow()
    val errorFlow = _errorFlow.asSharedFlow()

    init {
        getSurveyList()
    }

    private fun getSurveyList() {
        viewModelScope.launch {
            getSurveyListUseCase()
                .onSuccess { surveyList ->
                    _surveyListUiState.value = SurveyListUiState.Success(surveyList)
                }
                .onFailure { throwable ->
                    _errorFlow.emit(throwable)
                }
        }
    }

    sealed class SurveyListUiState {
        data object Init : SurveyListUiState()
        data class Success(val surveyList: List<Survey>) : SurveyListUiState()
    }
}
