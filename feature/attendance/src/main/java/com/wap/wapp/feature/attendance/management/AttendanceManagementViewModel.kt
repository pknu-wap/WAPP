package com.wap.wapp.feature.attendance.management

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wap.wapp.core.commmon.util.DateUtil
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
) : ViewModel() {
    private val _errorFlow: MutableSharedFlow<Throwable> = MutableSharedFlow()
    val errorFlow: SharedFlow<Throwable> = _errorFlow.asSharedFlow()

    private val _todayEventList = MutableStateFlow<EventsState>(EventsState.Loading)
    val todayEvents: StateFlow<EventsState> = _todayEventList.asStateFlow()

    init {
        getTodayDateEvents()
    }

    private fun getTodayDateEvents() = viewModelScope.launch {
        getDateEventListUseCase(DateUtil.generateNowDate()).onSuccess { eventList ->
            val unfinishedEventList = eventList.filter { !it.isAfterEndTime() }
            _todayEventList.value = EventsState.Success(unfinishedEventList)
        }.onFailure { exception -> _errorFlow.emit(exception) }
    }

    sealed class EventsState {
        data object Loading : EventsState()
        data class Success(val events: List<Event>) : EventsState()
    }
}
