package com.wap.wapp.feature.notice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wap.wapp.core.domain.usecase.event.GetEventsUseCase
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
    private val getEventsUseCase: GetEventsUseCase,
    private val dateUtil: DateUtil,
) : ViewModel() {

    private val _monthEvents = MutableStateFlow<EventsState>(EventsState.Loading)
    val monthEvents: StateFlow<EventsState> = _monthEvents.asStateFlow()

    private val _selectedDateEvents = MutableStateFlow<EventsState>(EventsState.Loading)
    val selectedDateEvent: StateFlow<EventsState> = _selectedDateEvents.asStateFlow()

    private val _selectedDates = MutableStateFlow<LocalDate>(dateUtil.generateNowDate())
    val selectedDates: StateFlow<LocalDate> = _selectedDates.asStateFlow()

    init {
        getMonthEvents()
        getSelectedDateEvents()
    }

    private fun getMonthEvents() {
        _monthEvents.value = EventsState.Loading
        viewModelScope.launch {
            getEventsUseCase(_selectedDates.value).fold(
                onSuccess = { _monthEvents.value = EventsState.Success(it) },
                onFailure = { _monthEvents.value = EventsState.Failure(it) },
            )
        }
    }

    fun getSelectedDateEvents() {
        _selectedDateEvents.value =
            when (_monthEvents.value) {
                is EventsState.Success ->
                    EventsState.Success(
                        (_monthEvents.value as EventsState.Success).events.filter {
                            it.period == _selectedDates.value
                        }.also {
                            it
                        },
                    )

                is EventsState.Failure ->
                    EventsState.Failure((_monthEvents.value as EventsState.Failure).throwable)

                is EventsState.Loading -> EventsState.Loading
            }
    }

    sealed class EventsState {
        data object Loading : EventsState()
        data class Success(val events: List<Event>) : EventsState()
        data class Failure(val throwable: Throwable) : EventsState()
    }
}
