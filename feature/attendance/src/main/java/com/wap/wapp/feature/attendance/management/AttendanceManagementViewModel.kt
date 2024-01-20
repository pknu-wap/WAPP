package com.wap.wapp.feature.attendance.management

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wap.wapp.core.commmon.util.DateUtil
import com.wap.wapp.core.commmon.util.DateUtil.generateNowDateTime
import com.wap.wapp.core.domain.usecase.attendance.PostAttendanceUseCase
import com.wap.wapp.core.domain.usecase.event.GetDateEventListUseCase
import com.wap.wapp.core.model.event.Event
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
class AttendanceManagementViewModel @Inject constructor(
    private val getDateEventListUseCase: GetDateEventListUseCase,
    private val postAttendanceUseCase: PostAttendanceUseCase,
) : ViewModel() {
    private val _errorFlow: MutableSharedFlow<Throwable> = MutableSharedFlow()
    val errorFlow: SharedFlow<Throwable> = _errorFlow.asSharedFlow()

    private val _attendanceManagementEvent: MutableSharedFlow<AttendanceManagementEvent> =
        MutableSharedFlow()
    val attendanceManagementEvent = _attendanceManagementEvent.asSharedFlow()

    private val _todayEventList = MutableStateFlow<EventsState>(EventsState.Loading)
    val todayEvents: StateFlow<EventsState> = _todayEventList.asStateFlow()

    private val _attendanceCode = MutableStateFlow<String>("")
    val attendanceCode: StateFlow<String> = _attendanceCode.asStateFlow()

    private val selectedEventId = MutableStateFlow<String>("")

    private val _selectedEventTitle = MutableStateFlow<String>("")
    val selectedEventTitle: StateFlow<String> = _selectedEventTitle.asStateFlow()

    init {
        getTodayDateEvents()
    }

    private fun getTodayDateEvents() = viewModelScope.launch {
        getDateEventListUseCase(DateUtil.generateNowDate()).onSuccess { eventList ->
            val unfinishedEventList = eventList.filter { it.isBeforeEndTime() }
            _todayEventList.value = EventsState.Success(unfinishedEventList)
        }.onFailure { exception -> _errorFlow.emit(exception) }
    }

    fun setAttendanceCode(attendanceCode: String) {
        if (attendanceCode.isDigitsOnly()) {
            _attendanceCode.value = attendanceCode
        }
    }

    fun clearAttendanceCode() { _attendanceCode.value = "" }

    fun setSelectedEventId(eventId: String) { selectedEventId.value = eventId }

    fun setSelectedEventTitle(eventTitle: String) { _selectedEventTitle.value = eventTitle }

    fun postAttendance() = viewModelScope.launch {
        postAttendanceUseCase(
            eventId = selectedEventId.value,
            code = _attendanceCode.value,
            deadline = generateNowDateTime().plusMinutes(10),
        ).onSuccess {
            _attendanceManagementEvent.emit(AttendanceManagementEvent.Success)
        }.onFailure { exception -> _errorFlow.emit(exception) }
    }

    sealed class EventsState {
        data object Loading : EventsState()
        data class Success(val events: List<Event>) : EventsState()
    }

    sealed class AttendanceManagementEvent {
        data object Success : AttendanceManagementEvent()
    }
}
