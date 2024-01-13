package com.wap.wapp.feature.management

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wap.wapp.core.commmon.util.DateUtil.generateNowDate
import com.wap.wapp.core.domain.usecase.event.GetMonthEventListUseCase
import com.wap.wapp.core.domain.usecase.management.HasManagerStateUseCase
import com.wap.wapp.core.domain.usecase.survey.GetSurveyFormListUseCase
import com.wap.wapp.core.model.event.Event
import com.wap.wapp.core.model.survey.SurveyForm
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
class ManagementViewModel @Inject constructor(
    private val hasManagerStateUseCase: HasManagerStateUseCase,
    private val getSurveyFormListUseCase: GetSurveyFormListUseCase,
    private val getMonthEventListUseCase: GetMonthEventListUseCase,
) : ViewModel() {

    private val _errorFlow: MutableSharedFlow<Throwable> = MutableSharedFlow()
    val errorFlow: SharedFlow<Throwable> = _errorFlow.asSharedFlow()

    private val _managerState: MutableStateFlow<ManagerState> =
        MutableStateFlow(ManagerState.Init)
    val managerState: StateFlow<ManagerState> = _managerState.asStateFlow()

    private val _surveyFormList: MutableStateFlow<SurveyFormsState> =
        MutableStateFlow(SurveyFormsState.Loading)
    val surveyFormList: StateFlow<SurveyFormsState> = _surveyFormList.asStateFlow()

    private val _eventList: MutableStateFlow<EventsState> = MutableStateFlow(EventsState.Loading)
    val eventList: StateFlow<EventsState> = _eventList.asStateFlow()

    init {
        hasManagerState()
        _managerState.value = ManagerState.Manager
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

    fun getEventSurveyList() = viewModelScope.launch {
        launch { getMonthEventList() }
        getSurveyFormList()
    }

    private suspend fun getMonthEventList() {
        _eventList.value = EventsState.Loading

        getMonthEventListUseCase(generateNowDate()).onSuccess { events ->
            _eventList.value = EventsState.Success(events)
        }.onFailure { exception ->
            _errorFlow.emit(exception)
            _eventList.value = EventsState.Failure(exception)
        }
    }

    private suspend fun getSurveyFormList() {
        _surveyFormList.value = SurveyFormsState.Loading

        getSurveyFormListUseCase().onSuccess { surveyForms ->
            _surveyFormList.value = SurveyFormsState.Success(surveyForms)
        }.onFailure { exception ->
            _errorFlow.emit(exception)
            _eventList.value = EventsState.Failure(exception)
        }
    }

    sealed class ManagerState {
        data object Init : ManagerState()
        data object Manager : ManagerState()
        data object NonManager : ManagerState()
    }

    sealed class EventsState {
        data object Loading : EventsState()
        data class Success(val events: List<Event>) : EventsState()
        data class Failure(val throwable: Throwable) : EventsState()
    }

    sealed class SurveyFormsState {
        data object Loading : SurveyFormsState()
        data class Success(val surveyForms: List<SurveyForm>) : SurveyFormsState()
        data class Failure(val throwable: Throwable) : SurveyFormsState()
    }
}
