package com.wap.wapp.feature.attendance

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wap.wapp.core.commmon.util.DateUtil
import com.wap.wapp.core.domain.usecase.attendance.GetEventListAttendanceUseCase
import com.wap.wapp.core.domain.usecase.attendance.ValidationAttendanceCodeUseCase
import com.wap.wapp.core.domain.usecase.attendancestatus.GetEventListAttendanceStatusUseCase
import com.wap.wapp.core.domain.usecase.attendancestatus.PostAttendanceStatusUseCase
import com.wap.wapp.core.domain.usecase.event.GetDateEventListUseCase
import com.wap.wapp.core.domain.usecase.user.GetUserProfileUseCase
import com.wap.wapp.core.domain.usecase.user.GetUserRoleUseCase
import com.wap.wapp.core.model.event.Event
import com.wap.wapp.core.model.user.UserProfile
import com.wap.wapp.core.model.user.UserRole
import com.wap.wapp.feature.attendance.model.EventAttendanceStatus
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
class AttendanceViewModel @Inject constructor(
    private val getDateEventListUseCase: GetDateEventListUseCase,
    private val getEventListAttendanceUseCase: GetEventListAttendanceUseCase,
    private val getEventListAttendanceStatusUseCase: GetEventListAttendanceStatusUseCase,
    private val getUserRoleUseCase: GetUserRoleUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val postAttendanceStatusUseCase: PostAttendanceStatusUseCase,
    private val validationAttendanceCodeUseCase: ValidationAttendanceCodeUseCase,
) : ViewModel() {
    private val _errorFlow: MutableSharedFlow<Throwable> = MutableSharedFlow()
    val errorFlow: SharedFlow<Throwable> = _errorFlow.asSharedFlow()

    private val _attendanceEvent: MutableSharedFlow<AttendanceEvent> = MutableSharedFlow()
    val attendanceEvent = _attendanceEvent.asSharedFlow()

    private val _userProfile = MutableStateFlow<UserProfile>(DEFAULT_USER_PROFILE)
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

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

    init {
        checkUserInformationAndGetEvents()
    }

    private fun checkUserInformationAndGetEvents() = viewModelScope.launch {
        getUserRoleUseCase().onSuccess { userRole ->
            when (userRole) {
                UserRole.GUEST -> _userRole.value = UserRoleState.Success(userRole)

                // 일반 회원 혹은 운영진 일 경우,
                // 유저 프로필을 받아오고, 해당 유저 아이디를 이용해서 출석 정보를 가져옴
                UserRole.MEMBER, UserRole.MANAGER -> {
                    async { getUserProfileUseCase() }.await().onSuccess {
                        _userRole.value = UserRoleState.Success(userRole)
                        _userProfile.value = it
                        launch { getTodayEventsAttendanceStatus() }
                    }.onFailure { exception -> _errorFlow.emit(exception) }
                }
            }
        }.onFailure { exception -> _errorFlow.emit(exception) }
    }

    fun getTodayEventsAttendanceStatus() {
        viewModelScope.launch {
            getDateEventListUseCase(DateUtil.generateNowDate()).onSuccess { eventList ->
                getEventListAttendance(eventList = eventList, userId = _userProfile.value.userId)
            }.onFailure { exception -> _errorFlow.emit(exception) }
        }
    }

    // 오늘 있는 일정을 기준으로, 출석이 시작된 일정들을 가져옵니다.
    private suspend fun getEventListAttendance(eventList: List<Event>, userId: String) =
        getEventListAttendanceUseCase(eventList.map { it.eventId })
            .onSuccess { attendanceList ->
                val filteredEventList = eventList.filter {
                    it.eventId in attendanceList.map { it.eventId }
                }

                val eventAttendanceList = filteredEventList.zip(attendanceList)
                    .map { (event, attendance) ->
                        EventAttendanceStatus(
                            eventId = event.eventId,
                            title = event.title,
                            content = event.content,
                            remainAttendanceDateTime = attendance.calculateDeadline(),
                        )
                    }

                getEventListAttendanceStatus(
                    eventAttendanceList = eventAttendanceList,
                    userId = userId,
                )
            }.onFailure { exception -> _errorFlow.emit(exception) }

    // 출석이 시작된 일정들 중, 유저가 출석을 했는 지 안했는 지를 판별합니다.
    private suspend fun getEventListAttendanceStatus(
        eventAttendanceList: List<EventAttendanceStatus>,
        userId: String,
    ) = getEventListAttendanceStatusUseCase(
        eventIdList = eventAttendanceList.map { it.eventId },
        userId = userId,
    ).onSuccess { attendanceStatusList ->
        val eventAttendanceStatusList = eventAttendanceList
            .zip(attendanceStatusList)
            .map { (eventAttendanceStatus, attendanceStatus) ->
                EventAttendanceStatus(
                    eventId = eventAttendanceStatus.eventId,
                    title = eventAttendanceStatus.title,
                    content = eventAttendanceStatus.content,
                    remainAttendanceDateTime = eventAttendanceStatus.remainAttendanceDateTime,
                    isAttendance = attendanceStatus.isAttendance(),
                )
            }

        _todayEventsAttendanceStatus.value =
            EventAttendanceStatusState.Success(eventAttendanceStatusList)
    }.onFailure { _errorFlow.emit(it) }

    fun verifyAttendanceCode() = viewModelScope.launch {
        validationAttendanceCodeUseCase(
            eventId = _selectedEventId.value,
            attendanceCode = _attendanceCode.value,
        ).onSuccess { result ->
            // 출석에 성공했을 경우
            if (result) {
                postAttendanceStatusUseCase(
                    eventId = _selectedEventId.value,
                    userId = _userProfile.value.userId,
                )
                    .onSuccess { _attendanceEvent.emit(AttendanceEvent.Success) }
                    .onFailure { exception -> _errorFlow.emit(exception) }
                return@launch
            }
            // 출석에 실패했을 경우
            _attendanceEvent.emit(AttendanceEvent.Failure("출석 코드가 일치하지 않습니다."))
        }.onFailure { exception -> _errorFlow.emit(exception) }
    }

    fun setAttendanceCode(attendanceCode: String) {
        if (attendanceCode.isDigitsOnly()) {
            _attendanceCode.value = attendanceCode
        }
    }

    fun clearAttendanceCode() { _attendanceCode.value = "" }

    fun setSelectedEventId(eventId: String) { _selectedEventId.value = eventId }

    fun setSelectedEventTitle(eventTitle: String) { _selectedEventTitle.value = eventTitle }

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

    companion object {
        val DEFAULT_USER_PROFILE = UserProfile("", "", "", "")
    }
}
