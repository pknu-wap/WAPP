package com.wap.wapp.feature.management

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wap.wapp.core.commmon.util.DateUtil.generateNowDate
import com.wap.wapp.core.domain.usecase.event.GetMonthEventListUseCase
import com.wap.wapp.core.domain.usecase.survey.GetSurveyFormListUseCase
import com.wap.wapp.core.domain.usecase.user.GetUserRoleUseCase
import com.wap.wapp.core.model.event.Event
import com.wap.wapp.core.model.survey.SurveyForm
import com.wap.wapp.core.model.user.UserRole
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
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
    private val getUserRoleUseCase: GetUserRoleUseCase,
    private val getSurveyFormListUseCase: GetSurveyFormListUseCase,
    private val getMonthEventListUseCase: GetMonthEventListUseCase,
) : ViewModel() {
    private val _errorFlow: MutableSharedFlow<Throwable> = MutableSharedFlow()
    val errorFlow: SharedFlow<Throwable> = _errorFlow.asSharedFlow()

    private val _userRole: MutableStateFlow<UserRoleUiState> =
        MutableStateFlow(UserRoleUiState.Init)
    val userRole: StateFlow<UserRoleUiState> = _userRole.asStateFlow()

    private val _surveyFormList: MutableStateFlow<SurveyFormsState> =
        MutableStateFlow(SurveyFormsState.Init)
    val surveyFormList: StateFlow<SurveyFormsState> = _surveyFormList.asStateFlow()

    private val _eventList: MutableStateFlow<EventsState> = MutableStateFlow(EventsState.Init)
    val eventList: StateFlow<EventsState> = _eventList.asStateFlow()

    fun getUserRole() = viewModelScope.launch {
        getUserRoleUseCase()
            .onSuccess { userRole ->
                _userRole.value = UserRoleUiState.Success(userRole)
            }
            .onFailure { exception ->
                _errorFlow.emit(exception)
            }
    }

    fun getEventSurveyList() = viewModelScope.launch {
        _eventList.value = EventsState.Loading
        _surveyFormList.value = SurveyFormsState.Loading

        val monthEventList = async { getMonthEventList() }
        val surveyFormList = async { getSurveyFormList() }

        _eventList.value = monthEventList.await()
        _surveyFormList.value = surveyFormList.await()
    }

    private suspend fun getMonthEventList(): EventsState =
        getMonthEventListUseCase(generateNowDate()).fold(
            onSuccess = { events ->
                EventsState.Success(events)
            },
            onFailure = { exception ->
                _errorFlow.emit(exception)
                EventsState.Failure(exception)
            },
        )

    private suspend fun getSurveyFormList(): SurveyFormsState = getSurveyFormListUseCase().fold(
        onSuccess = { surveyForms ->
            SurveyFormsState.Success(surveyForms)
        },
        onFailure = { exception ->
            _errorFlow.emit(exception)
            SurveyFormsState.Failure(exception)
        },
    )

    sealed class UserRoleUiState {
        data object Init : UserRoleUiState()
        data class Success(val userRole: UserRole) : UserRoleUiState()
    }

    sealed class EventsState {
        data object Init : EventsState()
        data object Loading : EventsState()
        data class Success(val events: List<Event>) : EventsState()
        data class Failure(val throwable: Throwable) : EventsState()
    }

    sealed class SurveyFormsState {
        data object Init : SurveyFormsState()
        data object Loading : SurveyFormsState()
        data class Success(val surveyForms: List<SurveyForm>) : SurveyFormsState()
        data class Failure(val throwable: Throwable) : SurveyFormsState()
    }
}
