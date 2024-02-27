package com.wap.wapp.feature.management.event.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wap.wapp.core.commmon.util.DateUtil.generateNowDate
import com.wap.wapp.core.commmon.util.DateUtil.generateNowTime
import com.wap.wapp.core.domain.usecase.event.PostEventUseCase
import com.wap.wapp.feature.management.event.registration.EventRegistrationState.EVENT_DETAILS
import com.wap.wapp.feature.management.event.registration.EventRegistrationState.EVENT_SCHEDULE
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
    private val postEventUseCase: PostEventUseCase,
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
        MutableStateFlow(generateNowDate())
    val eventStartDate = _eventStartDate.asStateFlow()

    private val _eventStartTime: MutableStateFlow<LocalTime> =
        MutableStateFlow(generateNowTime())
    val eventStartTime = _eventStartTime.asStateFlow()

    private val _eventEndDate: MutableStateFlow<LocalDate> =
        MutableStateFlow(generateNowDate())
    val eventEndDate = _eventEndDate.asStateFlow()

    private val _eventEndTime: MutableStateFlow<LocalTime> =
        MutableStateFlow(generateNowTime().plusHours(1))
    val eventEndTime = _eventEndTime.asStateFlow()

    fun setEventTitle(eventTitle: String) { _eventTitle.value = eventTitle }

    fun setEventContent(eventContent: String) { _eventContent.value = eventContent }

    fun setEventLocation(eventLocation: String) { _eventLocation.value = eventLocation }

    fun setEventStartDate(eventDate: LocalDate) {
        if (!isValidStartDate(eventDate)) {
            emitValidationErrorMessage("최소 하루 이상 일정 날짜를 지정하세요.")
            return
        }
        _eventStartDate.value = eventDate
    }

    fun setEventStartTime(eventTime: LocalTime) { _eventStartTime.value = eventTime }

    fun setEventEndDate(eventDate: LocalDate) {
        if (!isValidEndDate(eventDate)) {
            emitValidationErrorMessage("종료 날짜는 시작 날짜와 같거나 더 늦어야 합니다.")
            return
        }
        _eventEndDate.value = eventDate
    }

    fun setEventEndTime(eventTime: LocalTime) {
        if (!isValidEndTime(eventTime)) {
            emitValidationErrorMessage("종료 날짜는 시작 날짜와 같거나 더 늦어야 합니다.")
            return
        }
        _eventEndTime.value = eventTime
    }

    fun validateEvent(eventRegistrationState: EventRegistrationState): Boolean {
        when (eventRegistrationState) {
            EVENT_DETAILS -> {
                if (!isValidTitle()) {
                    emitValidationErrorMessage("행사 이름을 입력하세요.")
                    return false
                }

                if (!isValidContent()) {
                    emitValidationErrorMessage("행사 내용을 입력하세요.")
                    return false
                }
            }

            EVENT_SCHEDULE -> {
                if (!isValidLocation()) {
                    emitValidationErrorMessage("장소를 입력하세요.")
                    return false
                }
                if (!isValidEndTime(_eventEndTime.value)) {
                    emitValidationErrorMessage("일정 종료는 시작보다 늦어야 합니다.")
                    return false
                }
            }
        }
        return true
    }

    fun setEventRegistrationState(eventRegistrationState: EventRegistrationState) {
        _currentRegistrationState.value = eventRegistrationState
    }

    fun registerEvent() = viewModelScope.launch {
        postEventUseCase(
            eventTitle = _eventTitle.value,
            eventContent = _eventContent.value,
            eventLocation = _eventLocation.value,
            eventStartDate = _eventStartDate.value,
            eventStartTime = _eventStartTime.value,
            eventEndDate = _eventEndDate.value,
            eventEndTime = _eventEndTime.value,
        ).onSuccess {
            _eventRegistrationEvent.emit(EventRegistrationEvent.Success)
        }.onFailure { throwable ->
            _eventRegistrationEvent.emit(EventRegistrationEvent.Failure(throwable))
        }
    }

    private fun isValidEndTime(eventTime: LocalTime): Boolean {
        val startDate = _eventStartDate.value
        val endDate = _eventEndDate.value

        if (startDate == endDate) {
            val startTime = _eventStartTime.value
            return eventTime > startTime
        }
        return startDate < endDate
    }

    private fun isValidEndDate(eventDate: LocalDate): Boolean = eventDate >= _eventStartDate.value

    private fun isValidStartDate(eventDate: LocalDate): Boolean = eventDate > generateNowDate()

    private fun isValidContent(): Boolean = _eventContent.value.isNotEmpty()

    private fun isValidTitle(): Boolean = _eventTitle.value.isNotEmpty()

    private fun isValidLocation(): Boolean = _eventLocation.value.isNotEmpty()

    private fun emitValidationErrorMessage(message: String) {
        viewModelScope.launch {
            _eventRegistrationEvent.emit(
                EventRegistrationEvent.ValidationError(message),
            )
        }
    }
}
