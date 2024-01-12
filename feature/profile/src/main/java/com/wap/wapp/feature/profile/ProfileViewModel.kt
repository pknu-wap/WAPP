package com.wap.wapp.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wap.wapp.core.commmon.util.DateUtil
import com.wap.wapp.core.domain.usecase.event.GetEventListUseCase
import com.wap.wapp.core.domain.usecase.user.GetUserProfileUseCase
import com.wap.wapp.core.domain.usecase.user.GetUserRoleUseCase
import com.wap.wapp.core.model.event.Event
import com.wap.wapp.core.model.user.UserProfile
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
class ProfileViewModel @Inject constructor(
    private val getUserRoleUseCase: GetUserRoleUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val getEventListUseCase: GetEventListUseCase,
) : ViewModel() {
    private val _errorFlow: MutableSharedFlow<Throwable> = MutableSharedFlow()
    val errorFlow: SharedFlow<Throwable> = _errorFlow.asSharedFlow()

    private val _todayEvents = MutableStateFlow<EventsState>(EventsState.Loading)
    val todayEvents: StateFlow<EventsState> = _todayEvents.asStateFlow()

    private val _userRole = MutableStateFlow<UserRoleState>(UserRoleState.Loading)
    val userRole: StateFlow<UserRoleState> = _userRole.asStateFlow()

    private val _userProfile = MutableStateFlow<UserProfile>(DEFAULT_USER_PROFILE)
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

    init {
        checkUserInformationAndGetEvents()
    }

    private fun checkUserInformationAndGetEvents() = viewModelScope.launch {
        getUserRoleUseCase()
            .onFailure { exception -> _errorFlow.emit(exception) }
            .onSuccess { userRole ->
                when (userRole) {
                    // 비회원 일 경우, 바로 UserCard 갱신
                    UserRole.GUEST -> _userRole.value = UserRoleState.Success(userRole)

                    // 일반 회원 혹은 운영진 일 경우,
                    // 오늘 일정 정보, UserProfile 정보를 가져온 뒤 한꺼번에 갱신
                    UserRole.MEMBER, UserRole.MANAGER -> {
                        getTodayDateEvents()
                        val userProfile = async { getUserProfileUseCase() }

                        userProfile.await()
                            .onSuccess {
                                _userRole.value = UserRoleState.Success(userRole)
                                _userProfile.value = it
                            }.onFailure { exception -> _errorFlow.emit(exception) }
                    }
                }
            }
    }

    private fun getTodayDateEvents() {
        _todayEvents.value = EventsState.Loading
        viewModelScope.launch {
            getEventListUseCase(DateUtil.generateNowDate()).onSuccess { eventList ->
                _todayEvents.value =
                    EventsState.Success(
                        eventList.filter { event ->
                            event.startDateTime == DateUtil.generateNowDateTime()
                        },
                    )
            }.onFailure { exception -> _errorFlow.emit(exception) }
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

    companion object {
        val DEFAULT_USER_PROFILE = UserProfile("", "", "", "")
    }
}
