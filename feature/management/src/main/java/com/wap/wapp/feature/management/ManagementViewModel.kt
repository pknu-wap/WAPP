package com.wap.wapp.feature.management

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wap.wapp.core.domain.usecase.management.HasManagerStateUseCase
import com.wap.wapp.core.domain.usecase.survey.GetSurveyListUseCase
import com.wap.wapp.core.model.survey.Survey
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ManagementViewModel @Inject constructor(
    private val hasManagerStateUseCase: HasManagerStateUseCase,
    private val getSurveyListUseCase: GetSurveyListUseCase,
) : ViewModel() {

    private val _errorFlow: MutableSharedFlow<Throwable> = MutableSharedFlow()
    val errorFlow: SharedFlow<Throwable> = _errorFlow.asSharedFlow()

    private val _managerState: MutableStateFlow<ManagerState> = MutableStateFlow(ManagerState.Init)
    val managerState: StateFlow<ManagerState> = _managerState.asStateFlow()

    private val _surveyList: MutableStateFlow<List<Survey>> = MutableStateFlow(emptyList())
    val surveyList: StateFlow<List<Survey>> = _surveyList.asStateFlow()

    init {
        hasManagerState()
    }

    private fun hasManagerState() {
        viewModelScope.launch {
            hasManagerStateUseCase()
                .onSuccess { hasManageState ->
                    if (hasManageState) {
                        _managerState.emit(ManagerState.Manager)
                    } else {
                        _managerState.emit(ManagerState.NonManager)
                    }
                }
                .onFailure { exception ->
                    _errorFlow.emit(exception)
                }
        }
    }

    fun getSurveyList() {
        viewModelScope.launch {
            getSurveyListUseCase()
                .onSuccess { surveyList ->
                    _surveyList.emit(surveyList)
                }
                .onFailure { exception ->
                    _errorFlow.emit(exception)
                }
        }
    }

    sealed class ManagerState {
        data object Init : ManagerState()
        data object Manager : ManagerState()
        data object NonManager : ManagerState()
    }
}