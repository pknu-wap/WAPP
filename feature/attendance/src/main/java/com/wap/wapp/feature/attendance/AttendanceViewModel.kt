package com.wap.wapp.feature.attendance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wap.wapp.core.commmon.util.DateUtil
import com.wap.wapp.core.domain.usecase.event.GetDateEventListUseCase
import com.wap.wapp.core.domain.usecase.user.GetUserProfileUseCase
import com.wap.wapp.core.model.event.Event
import com.wap.wapp.core.model.user.UserProfile
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
    private val getUserProfileUseCase: GetUserProfileUseCase,
) : ViewModel() {
    private val _errorFlow: MutableSharedFlow<Throwable> = MutableSharedFlow()
    val errorFlow: SharedFlow<Throwable> = _errorFlow.asSharedFlow()

    private val _userProfile = MutableStateFlow<UserProfileState>(UserProfileState.Loading)
    val userProfile: StateFlow<UserProfileState> = _userProfile.asStateFlow()

    private val _todayEvents = MutableStateFlow<EventsState>(EventsState.Loading)
    val todayEvents: StateFlow<EventsState> = _todayEvents.asStateFlow()

    init {
        getTodayDateEvents()
        getUserProfile()
    }

    private fun getTodayDateEvents() = viewModelScope.launch {
        getDateEventListUseCase(DateUtil.generateNowDate()).onSuccess { eventList ->
            _todayEvents.value = EventsState.Success(eventList)
        }.onFailure { exception -> _errorFlow.emit(exception) }
    }

    private fun getUserProfile() = viewModelScope.launch {
        getUserProfileUseCase().onSuccess {
            _userProfile.value = UserProfileState.Success(it)
        }.onFailure { exception -> _errorFlow.emit(exception) }
    }

    sealed class EventsState {
        data object Loading : EventsState()
        data class Success(val events: List<Event>) : EventsState()
    }

    sealed class UserProfileState {
        data object Loading : UserProfileState()
        data class Success(val userProfile: UserProfile) : UserProfileState()
    }
}
