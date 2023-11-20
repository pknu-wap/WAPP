package com.wap.wapp.feature.notice

import android.util.Log
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
import javax.inject.Inject

@HiltViewModel
class NoticeViewModel @Inject constructor(
    private val getEventsUseCase: GetEventsUseCase,
) : ViewModel() {

    private val _signUpEventFlow = MutableSharedFlow<NoticeEvent>()
    val signUpEventFlow: SharedFlow<NoticeEvent> = _signUpEventFlow.asSharedFlow()

    private val _events = MutableStateFlow<EventsState>(EventsState.Loading)
    val events: StateFlow<EventsState> = _events.asStateFlow()

    init {
        getNowMonthEvents()
    }

    fun event(event: NoticeEvent) = viewModelScope.launch { _signUpEventFlow.emit(event) }

    fun getNowMonthEvents() {
        Log.d("test", "NoticeViewModel : $events")
        _events.value = EventsState.Loading
        viewModelScope.launch {
            getEventsUseCase().fold(
                onSuccess = { _events.value = EventsState.Success(it) },
                onFailure = { _events.value = EventsState.Failure(it) },
            )
            Log.d("test", "NoticeViewModel : $events")
        }
    }

    sealed class NoticeEvent

    sealed class EventsState {
        data object Loading : EventsState()
        data class Success(val events: List<Event>) : EventsState()
        data class Failure(val throwable: Throwable) : EventsState()
    }
}
