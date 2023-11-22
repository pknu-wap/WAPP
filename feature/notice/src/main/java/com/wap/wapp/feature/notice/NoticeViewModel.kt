package com.wap.wapp.feature.notice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wap.wapp.core.domain.usecase.event.GetEventsUseCase
import com.wap.wapp.core.model.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class NoticeViewModel @Inject constructor(
    private val getEventsUseCase: GetEventsUseCase,
    private val dateUtil: DateUtil,
) : ViewModel() {

    private val _signUpEventFlow = MutableSharedFlow<NoticeEvent>()
    val signUpEventFlow: SharedFlow<NoticeEvent> = _signUpEventFlow.asSharedFlow()

    private val _events = MutableStateFlow<EventsState>(EventsState.Loading)
    val events: StateFlow<EventsState> = _events.asStateFlow()

    private val _selectedDates = MutableStateFlow<LocalDate>(dateUtil.generateNowDate())
    val selectedDate: StateFlow<LocalDate> = _selectedDates.asStateFlow()

    init {
        getMonthEvents()
    }

    fun event(event: NoticeEvent) = viewModelScope.launch { _signUpEventFlow.emit(event) }

    fun getMonthEvents() {
        _events.value = EventsState.Loading
        viewModelScope.launch {
            getEventsUseCase(_selectedDates.value).fold(
                onSuccess = { _events.value = EventsState.Success(it) },
                onFailure = { _events.value = EventsState.Failure(it) },
            )
        }
    }

    sealed class NoticeEvent

    sealed class EventsState {
        data object Loading : EventsState()
        data class Success(val events: List<Event>) : EventsState()
        data class Failure(val throwable: Throwable) : EventsState()
    }
}
