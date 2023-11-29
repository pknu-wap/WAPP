package com.wap.wapp.feature.management.registration.event

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class EventRegistrationViewModel @Inject constructor() : ViewModel() {
    private val _currentRegistrationState: MutableStateFlow<EventRegistrationState> =
        MutableStateFlow(EventRegistrationState.EVENT_DETAILS)
    val currentRegistrationState = _currentRegistrationState.asStateFlow()

    private val _eventTitle: MutableStateFlow<String> = MutableStateFlow("")
    val eventTitle = _eventTitle.asStateFlow()

    private val _eventContent: MutableStateFlow<String> = MutableStateFlow("")
    val eventContent = _eventContent.asStateFlow()

    fun setEventTitle(eventTitle: String) {
        _eventTitle.value = eventTitle
    }

    fun setEventContent(eventContent: String) {
        _eventContent.value = eventContent
    }

    fun setEventRegistrationState(eventRegistrationState: EventRegistrationState) {
        _currentRegistrationState.value = eventRegistrationState
    }
}
