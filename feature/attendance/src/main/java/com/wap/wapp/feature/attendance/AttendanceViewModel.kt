package com.wap.wapp.feature.attendance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wap.wapp.core.commmon.util.DateUtil
import com.wap.wapp.core.domain.usecase.event.GetDateEventListUseCase
import com.wap.wapp.core.domain.usecase.user.GetUserRoleUseCase
import com.wap.wapp.core.model.event.Event
import com.wap.wapp.core.model.user.UserRole
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
class AttendanceViewModel @Inject constructor(
    private val getDateEventListUseCase: GetDateEventListUseCase,
    private val getUserRoleUseCase: GetUserRoleUseCase,
) : ViewModel() {
    private val _errorFlow: MutableSharedFlow<Throwable> = MutableSharedFlow()
    val errorFlow: SharedFlow<Throwable> = _errorFlow.asSharedFlow()

    private val _userRole = MutableStateFlow<UserRoleState>(UserRoleState.Loading)
    val userRole: StateFlow<UserRoleState> = _userRole.asStateFlow()

    private val _todayEventList = MutableStateFlow<EventsState>(EventsState.Loading)
    val todayEvents: StateFlow<EventsState> = _todayEventList.asStateFlow()

    init {
        getTodayDateEvents()
        getUserRole()
    }

    private fun getTodayDateEvents() = viewModelScope.launch {
        getDateEventListUseCase(DateUtil.generateNowDate()).onSuccess { eventList ->
            _todayEventList.value = EventsState.Success(eventList)
        }.onFailure { exception -> _errorFlow.emit(exception) }
    }

    private fun getUserRole() = viewModelScope.launch {
        getUserRoleUseCase().onSuccess {
            _userRole.value = UserRoleState.Success(it)
        }.onFailure { exception -> _errorFlow.emit(exception) }
    }

    sealed class EventsState {
        data object Loading : EventsState()
        data class Success(val events: List<Event>) : EventsState()
    }

    sealed class UserRoleState {
        data object Loading : UserRoleState()
        data class Success(val userRole: UserRole) : UserRoleState()
    }
}
