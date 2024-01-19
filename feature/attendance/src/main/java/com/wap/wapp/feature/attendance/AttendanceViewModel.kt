package com.wap.wapp.feature.attendance

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wap.wapp.core.commmon.util.DateUtil
import com.wap.wapp.core.domain.usecase.attendance.GetAttendanceUseCase
import com.wap.wapp.core.domain.usecase.attendance.VerifyAttendanceCodeUseCase
import com.wap.wapp.core.domain.usecase.attendancestatus.GetEventListAttendanceStatusUseCase
import com.wap.wapp.core.domain.usecase.event.GetDateEventListUseCase
import com.wap.wapp.core.domain.usecase.user.GetUserRoleUseCase
import com.wap.wapp.core.model.event.Event
import com.wap.wapp.core.model.user.UserRole
import com.wap.wapp.feature.attendance.model.EventAttendanceStatus
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
    private val getAttendanceUseCase: GetAttendanceUseCase,
    private val getEventListAttendanceStatusUseCase: GetEventListAttendanceStatusUseCase,
    private val getUserRoleUseCase: GetUserRoleUseCase,
    private val verifyAttendanceCodeUseCase: VerifyAttendanceCodeUseCase,
) : ViewModel() {
    private val _errorFlow: MutableSharedFlow<Throwable> = MutableSharedFlow()
    val errorFlow: SharedFlow<Throwable> = _errorFlow.asSharedFlow()

    private val _attendanceEvent: MutableSharedFlow<AttendanceEvent> =
        MutableSharedFlow()
    val attendanceEvent = _attendanceEvent.asSharedFlow()

    private val _userRole = MutableStateFlow<UserRoleState>(UserRoleState.Loading)
    val userRole: StateFlow<UserRoleState> = _userRole.asStateFlow()

    private val _todayEventsAttendanceStatus =
        MutableStateFlow<EventAttendanceStatusState>(EventAttendanceStatusState.Loading)
    val todayEventsAttendanceStatus: StateFlow<EventAttendanceStatusState> =
        _todayEventsAttendanceStatus.asStateFlow()

    private val _attendanceCode = MutableStateFlow<String>("")
    val attendanceCode: StateFlow<String> = _attendanceCode.asStateFlow()

    private val _selectedEventId = MutableStateFlow<String>("")

    private val _selectedEventTitle = MutableStateFlow<String>("")
    val selectedEventTitle: StateFlow<String> = _selectedEventTitle.asStateFlow()

    fun getTodayEventsAttendanceStatus(userId: String) = viewModelScope.launch {
        getDateEventListUseCase(DateUtil.generateNowDate()).onSuccess { eventList ->
            getEventListAttendanceStatus(eventList = eventList, userId = userId)
        }.onFailure { exception -> _errorFlow.emit(exception) }
    }

    private suspend fun getEventListAttendanceStatus(eventList: List<Event>, userId: String) =
        getEventListAttendanceStatusUseCase(
            eventIdList = eventList.map { it.eventId },
            userId = userId,
        ).onSuccess { attendanceStatusList ->
            val eventAttendanceStatusList = eventList
                .zip(attendanceStatusList)
                .map { (event, attendanceStatus) ->
                    EventAttendanceStatus(
                        eventId = event.eventId,
                        title = event.title,
                        content = event.content,
                        remainAttendanceDateTime = attendanceStatus.attendanceDateTime,
                        isAttendance = attendanceStatus.isAttendance(),
                    )
                }
            _todayEventsAttendanceStatus.value =
                EventAttendanceStatusState.Success(eventAttendanceStatusList)
        }.onFailure { _errorFlow.emit(it) }

    fun getUserRole() = viewModelScope.launch {
        getUserRoleUseCase().onSuccess {
            _userRole.value = UserRoleState.Success(it)
        }.onFailure { exception -> _errorFlow.emit(exception) }
    }

    fun setAttendanceCode(attendanceCode: String) {
        if (attendanceCode.isDigitsOnly()) {
            _attendanceCode.value = attendanceCode
        }
    }

    fun setSelectedEventId(eventId: String) {
        _selectedEventId.value = eventId
    }

    fun setSelectedEventTitle(eventTitle: String) {
        _selectedEventTitle.value = eventTitle
    }

    fun verifyAttendanceCode() = viewModelScope.launch {
        verifyAttendanceCodeUseCase(
            eventId = _selectedEventId.value,
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

    sealed class EventAttendanceStatusState {
        data object Loading : EventAttendanceStatusState()
        data class Success(val events: List<EventAttendanceStatus>) : EventAttendanceStatusState()
    }

    sealed class UserRoleState {
        data object Loading : UserRoleState()
        data class Success(val userRole: UserRole) : UserRoleState()
    }

    sealed class AttendanceEvent {
        data object Success : AttendanceEvent()
        data class Failure(val message: String) : AttendanceEvent()
    }
}
