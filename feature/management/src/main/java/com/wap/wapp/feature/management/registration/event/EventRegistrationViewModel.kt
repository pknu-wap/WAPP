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

    private val _eventStartDate: MutableStateFlow<LocalDate> =
        MutableStateFlow(DateUtil.generateNowDate())
    val eventStartDate = _eventStartDate.asStateFlow()

    private val _eventStartTime: MutableStateFlow<LocalTime> =
        MutableStateFlow(DateUtil.generateNowTime())
    val eventStartTime = _eventStartTime.asStateFlow()

    private val _eventEndDate: MutableStateFlow<LocalDate> =
        MutableStateFlow(DateUtil.generateNowDate())
    val eventEndDate = _eventEndDate.asStateFlow()

    private val _eventEndTime: MutableStateFlow<LocalTime> =
        MutableStateFlow(DateUtil.generateNowTime())
    val eventEndTime = _eventEndTime.asStateFlow()

    fun setEventTitle(eventTitle: String) {
        _eventTitle.value = eventTitle
    }

    fun setEventContent(eventContent: String) {
        _eventContent.value = eventContent
    }

    fun setEventLocation(eventLocation: String) {
        _eventLocation.value = eventLocation
    }

    fun setEventStartDate(eventDate: LocalDate) {
        _eventStartDate.value = eventDate
    }

    fun setEventStartTime(eventTime: LocalTime) {
        _eventStartTime.value = eventTime
    }

    fun setEventEndDate(eventDate: LocalDate) {
        _eventEndDate.value = eventDate
    }

    fun setEventEndTime(eventTime: LocalTime) {
        _eventEndTime.value = eventTime
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

        if (_eventEndDate.value < _eventStartDate.value) {
            emitValidationErrorMessage("종료 날짜는 시작 날짜와 같거나 더 늦어야 합니다.")
            return
        }

        if (
            (_eventEndDate.value == _eventStartDate.value) &&
            (_eventEndTime.value <= _eventStartTime.value)
        ) {
            emitValidationErrorMessage("종료 날짜는 시작 날짜와 같거나 더 늦어야 합니다.")
            return
        }

        if (_eventEndDate.value <= generateNowDate()) {
            emitValidationErrorMessage("최소 하루 이상 일정 날짜를 지정하세요.")
            return
        }

        viewModelScope.launch {
            registerEventUseCase(
                eventTitle = _eventTitle.value,
                eventContent = _eventContent.value,
                eventLocation = _eventLocation.value,
                eventStartDate = _eventStartDate.value,
                eventStartTime = _eventStartTime.value,
                eventEndDate = _eventEndDate.value,
                eventEndTime = _eventEndTime.value,
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
