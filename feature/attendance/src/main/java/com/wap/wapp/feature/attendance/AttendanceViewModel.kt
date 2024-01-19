package com.wap.wapp.feature.attendance

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wap.wapp.core.commmon.util.DateUtil
import com.wap.wapp.core.commmon.util.DateUtil.generateNowDateTime
import com.wap.wapp.core.domain.usecase.attendance.IsVerifyAttendanceCodeUseCase
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
    private val isVerifyAttendanceCodeUseCase: IsVerifyAttendanceCodeUseCase,
) : ViewModel() {
    private val _errorFlow: MutableSharedFlow<Throwable> = MutableSharedFlow()
    val errorFlow: SharedFlow<Throwable> = _errorFlow.asSharedFlow()

    private val _attendanceEvent: MutableSharedFlow<AttendanceEvent> =
        MutableSharedFlow()
    val attendanceEvent = _attendanceEvent.asSharedFlow()

    private val _userRole = MutableStateFlow<UserRoleState>(UserRoleState.Loading)
    val userRole: StateFlow<UserRoleState> = _userRole.asStateFlow()

    private val _todayEventList = MutableStateFlow<EventsState>(EventsState.Loading)
    val todayEvents: StateFlow<EventsState> = _todayEventList.asStateFlow()

    private val _attendanceCode = MutableStateFlow<String>("")
    val attendanceCode: StateFlow<String> = _attendanceCode.asStateFlow()

    private val _selectedEvent = MutableStateFlow<Event>(DEFAULT_EVENT)
    val selectedEvent: StateFlow<Event> = _selectedEvent.asStateFlow()

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

    fun setAttendanceCode(attendanceCode: String) {
        if (attendanceCode.isDigitsOnly()) {
            _attendanceCode.value = attendanceCode
        }
    }

    fun setSelectedEvent(event: Event) {
        _selectedEvent.value = event
    }

    fun verifyAttendanceCode() = viewModelScope.launch {
        isVerifyAttendanceCodeUseCase(
            eventId = _selectedEvent.value.eventId,
            attendanceCode = _attendanceCode.value,
        ).onSuccess { result ->
            // 출석에 성공했을 경우
            if (result) {
                _attendanceEvent.emit(AttendanceEvent.Success)
                return@launch
            }
            // 출석에 실패했을 경우
            _attendanceEvent.emit(AttendanceEvent.Failure("출석 코드가 일치하지 않습니다."))
        }.onFailure { exception ->
            // 네트워크 통신에 실패했을 경우
            _errorFlow.emit(exception)
        }
    }

    sealed class EventsState {
        data object Loading : EventsState()
        data class Success(val events: List<Event>) : EventsState()
    }

    sealed class UserRoleState {
        data object Loading : UserRoleState()
        data class Success(val userRole: UserRole) : UserRoleState()
    }

    sealed class AttendanceEvent {
        data object Success : AttendanceEvent()
        data class Failure(val message: String) : AttendanceEvent()
    }

    companion object {
        val DEFAULT_EVENT =
            Event("", "", "", "", generateNowDateTime(), generateNowDateTime())
    }
}
