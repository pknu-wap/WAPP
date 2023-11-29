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

    private val _eventLocation: MutableStateFlow<String> = MutableStateFlow("")
    val eventLocation = _eventLocation.asStateFlow()

    private val _eventDate: MutableStateFlow<String> = MutableStateFlow("")
    val eventDate = _eventDate.asStateFlow()

    private val _eventTime: MutableStateFlow<String> = MutableStateFlow("")
    val eventTime = _eventTime.asStateFlow()

    fun setEventTitle(eventTitle: String) {
        _eventTitle.value = eventTitle
    }

    fun setEventContent(eventContent: String) {
        _eventContent.value = eventContent
    }

    fun setEventLocation(eventLocation: String) {
        _eventLocation.value = eventLocation
    }

    fun setEventDate(eventDate: String) {
        _eventDate.value = eventDate
    }

    fun setEventTime(eventTime: String) {
        _eventTime.value = eventTime
    }

    fun setEventRegistrationState(eventRegistrationState: EventRegistrationState) {
        _currentRegistrationState.value = eventRegistrationState
    }

    fun registerEvent() {}
}
