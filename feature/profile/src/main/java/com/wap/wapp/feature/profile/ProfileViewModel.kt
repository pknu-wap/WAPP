package com.wap.wapp.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wap.wapp.core.commmon.util.DateUtil
import com.wap.wapp.core.domain.usecase.event.GetMonthEventListUseCase
import com.wap.wapp.core.model.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getMonthEventListUseCase: GetMonthEventListUseCase,
) : ViewModel() {
    private val _todayEvents = MutableStateFlow<EventsState>(EventsState.Loading)
    val todayEvents: StateFlow<EventsState> = _todayEvents.asStateFlow()

    init {
        getTodayDateEvents()
    }

    private fun getTodayDateEvents() {
        _todayEvents.value = EventsState.Loading
        viewModelScope.launch {
            getMonthEventListUseCase(DateUtil.generateNowDate()).fold(
                onSuccess = {
                    _todayEvents.value =
                        EventsState.Success(
                            it.filter {
                                it.endDateTime == DateUtil.generateNowDateTime()
                            },
                        )
                },
                onFailure = { _todayEvents.value = EventsState.Failure(it) },
            )
        }
    }

    sealed class EventsState {
        data object Loading : EventsState()
        data class Success(val events: List<Event>) : EventsState()
        data class Failure(val throwable: Throwable) : EventsState()
    }
}
