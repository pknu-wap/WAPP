package com.wap.wapp.feature.management.edit.event

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wap.wapp.core.commmon.util.DateUtil
import com.wap.wapp.core.domain.usecase.event.GetEventUseCase
import com.wap.wapp.core.domain.usecase.event.UpdateEventUseCase
import com.wap.wapp.feature.management.registration.event.EventRegistrationEvent
import com.wap.wapp.feature.management.registration.event.EventRegistrationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class EventEditViewModel @Inject constructor(
    private val getEventUseCase: GetEventUseCase,
    private val updateEventUseCase: UpdateEventUseCase,
) : ViewModel() {
    private val _currentEditState: MutableStateFlow<EventRegistrationState> =
        MutableStateFlow(EventRegistrationState.EVENT_DETAILS)
    val currentEditState = _currentEditState.asStateFlow()

    private val _eventEditEvent: MutableSharedFlow<EventRegistrationEvent> =
        MutableSharedFlow()
    val eventEditEvent = _eventEditEvent.asSharedFlow()

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
        MutableStateFlow(DateUtil.generateNowTime().plusHours(1))
    val eventEndTime = _eventEndTime.asStateFlow()

    private val _eventId: MutableStateFlow<String> = MutableStateFlow("")

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

    fun setEventRegistrationState() {
        if (_currentEditState.value == EventRegistrationState.EVENT_DETAILS) {
            if (!isValidTitle()) {
                emitValidationErrorMessage("행사 이름을 입력하세요.")
                return
            }
            if (!isValidContent()) {
                emitValidationErrorMessage("행사 내용을 입력하세요.")
                return
            }
            _currentEditState.value = EventRegistrationState.EVENT_SCHEDULE
        }
    }

    fun updateEvent() {
        if (!isValidLocation()) {
            emitValidationErrorMessage("장소를 입력하세요.")
            return
        }

        viewModelScope.launch {
            updateEventUseCase(
                eventTitle = _eventTitle.value,
                eventContent = _eventContent.value,
                eventLocation = _eventLocation.value,
                eventStartDate = _eventStartDate.value,
                eventStartTime = _eventStartTime.value,
                eventEndDate = _eventEndDate.value,
                eventEndTime = _eventEndTime.value,
                eventId = _eventId.value,
            ).onSuccess {
                _eventEditEvent.emit(EventRegistrationEvent.Success)
            }.onFailure { throwable ->
                _eventEditEvent.emit(EventRegistrationEvent.Failure(throwable))
            }
        }
    }

    private fun isValidEndTime(eventTime: LocalTime): Boolean =
        _eventEndDate.value == _eventStartDate.value && eventTime > _eventStartTime.value

    private fun isValidEndDate(eventDate: LocalDate): Boolean = eventDate >= _eventStartDate.value

    private fun isValidContent(): Boolean = _eventContent.value.isNotEmpty()

    private fun isValidTitle(): Boolean = _eventTitle.value.isNotEmpty()

    private fun isValidLocation(): Boolean = _eventLocation.value.isNotEmpty()

    fun getEvent(date: String, eventId: String) = viewModelScope.launch {
        val date = LocalDateTime.parse(date, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        getEventUseCase(date, eventId).onSuccess {
            _eventContent.value = it.content
            _eventTitle.value = it.title
            _eventStartDate.value = it.startDateTime.toLocalDate()
            _eventStartTime.value = it.startDateTime.toLocalTime()
            _eventEndDate.value = it.endDateTime.toLocalDate()
            _eventEndTime.value = it.endDateTime.toLocalTime()
            _eventLocation.value = it.location
            _eventId.value = it.eventId
        }
            .onFailure { emitValidationErrorMessage("이벤트를 불러오는데 실패하였습니다.") }
    }

    private fun emitValidationErrorMessage(message: String) {
        viewModelScope.launch {
            _eventEditEvent.emit(
                EventRegistrationEvent.ValidationError(message),
            )
        }
    }
}
