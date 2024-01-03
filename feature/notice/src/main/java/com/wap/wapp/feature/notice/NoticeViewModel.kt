package com.wap.wapp.feature.notice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wap.wapp.core.commmon.util.DateUtil
import com.wap.wapp.core.domain.usecase.event.GetEventListUseCase
import com.wap.wapp.core.model.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class NoticeViewModel @Inject constructor(
    private val getEventListUseCase: GetEventListUseCase,
) : ViewModel() {

    private val _monthEvents = MutableStateFlow<EventsState>(EventsState.Loading)
    val monthEvents: StateFlow<EventsState> = _monthEvents.asStateFlow()

    private val _selectedDateEvents = MutableStateFlow<EventsState>(EventsState.Loading)
    val selectedDateEvents: StateFlow<EventsState> = _selectedDateEvents.asStateFlow()

    private val _selectedDate = MutableStateFlow<LocalDate>(DateUtil.generateNowDate())
    val selectedDate: StateFlow<LocalDate> = _selectedDate.asStateFlow()

    init {
        getMonthEvents()
        getSelectedDateEvents()
    }

    private fun getMonthEvents() {
        _monthEvents.value = EventsState.Loading
        viewModelScope.launch {
            getEventListUseCase(_selectedDate.value)
                .onSuccess { _monthEvents.value = EventsState.Success(it) }
                .onFailure { _monthEvents.value = EventsState.Failure(it) }
        }
    }

    fun getSelectedDateEvents() {
        _selectedDateEvents.value = EventsState.Loading
        viewModelScope.launch {
            getEventListUseCase(_selectedDate.value).onSuccess {
                _selectedDateEvents.value = EventsState.Success(
                    it.filter { it.endDateTime.toLocalDate() == _selectedDate.value },
                )
            }.onFailure { _selectedDateEvents.value = EventsState.Failure(it) }
        }
    }

    fun updateSelectedDate(newSelectedDate: LocalDate) {
        _selectedDate.value = newSelectedDate
        getSelectedDateEvents()
    }

    sealed class EventsState {
        data object Loading : EventsState()
        data class Success(val events: List<Event>) : EventsState()
        data class Failure(val throwable: Throwable) : EventsState()
    }
}
