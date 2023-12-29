package com.wap.wapp.feature.management.registration.event

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wap.wapp.core.commmon.util.DateUtil
import com.wap.wapp.core.commmon.util.DateUtil.generateNowDate
import com.wap.wapp.core.domain.usecase.event.RegisterEventUseCase
import com.wap.wapp.feature.management.registration.event.EventRegistrationState.EVENT_DETAILS
import com.wap.wapp.feature.management.registration.event.EventRegistrationState.EVENT_SCHEDULE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class EventRegistrationViewModel @Inject constructor(
    private val registerEventUseCase: RegisterEventUseCase,
) : ViewModel() {
    private val _currentRegistrationState: MutableStateFlow<EventRegistrationState> =
        MutableStateFlow(EVENT_DETAILS)
    val currentRegistrationState = _currentRegistrationState.asStateFlow()

    private val _eventRegistrationEvent: MutableSharedFlow<EventRegistrationEvent> =
        MutableSharedFlow()
    val eventRegistrationEvent = _eventRegistrationEvent.asSharedFlow()

    private val _eventTitle: MutableStateFlow<String> = MutableStateFlow("")
    val eventTitle = _eventTitle.asStateFlow()

    private val _eventContent: MutableStateFlow<String> = MutableStateFlow("")
    val eventContent = _eventContent.asStateFlow()

    private val _eventLocation: MutableStateFlow<String> = MutableStateFlow("")
    val eventLocation = _eventLocation.asStateFlow()

    private val _eventDate: MutableStateFlow<LocalDate> =
        MutableStateFlow(DateUtil.generateNowDate())
    val eventDate = _eventDate.asStateFlow()

    private val _eventTime: MutableStateFlow<LocalTime> =
        MutableStateFlow(DateUtil.generateNowTime())
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

    fun setEventDate(eventDate: LocalDate) {
        _eventDate.value = eventDate
    }

    fun setEventTime(eventTime: LocalTime) {
        _eventTime.value = eventTime
    }

    fun setEventRegistrationState() {
        if (_currentRegistrationState.value == EVENT_DETAILS) {
            if (_eventTitle.value.isEmpty()) {
                emitValidationErrorMessage("행사 이름을 입력하세요.")
                return
            }
            if (_eventContent.value.isEmpty()) {
                emitValidationErrorMessage("행사 내용을 입력하세요.")
                return
            }
            _currentRegistrationState.value = EVENT_SCHEDULE
        }
    }

    fun registerEvent() {
        if (_eventLocation.value.isEmpty()) {
            emitValidationErrorMessage("장소를 입력하세요.")
            return
        }

        if (_eventDate.value <= generateNowDate()) {
            emitValidationErrorMessage("최소 하루 이상 일정 날짜를 지정하세요.")
            return
        }

        viewModelScope.launch {
            registerEventUseCase(
                date = generateNowDate(),
                eventTitle = _eventTitle.value,
                eventContent = _eventContent.value,
                eventLocation = _eventLocation.value,
                eventDate = _eventDate.value,
                eventTime = _eventTime.value,
            ).fold(
                onSuccess = { _eventRegistrationEvent.emit(EventRegistrationEvent.Success) },
                onFailure = { throwable ->
                    _eventRegistrationEvent.emit(EventRegistrationEvent.Failure(throwable))
                },
            )
        }
    }

    private fun emitValidationErrorMessage(message: String) {
        viewModelScope.launch {
            _eventRegistrationEvent.emit(
                EventRegistrationEvent.ValidationError(message),
            )
        }
    }
}
