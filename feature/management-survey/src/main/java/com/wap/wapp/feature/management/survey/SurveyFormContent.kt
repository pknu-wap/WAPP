package com.wap.wapp.feature.management.survey

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.wap.wapp.core.model.event.Event
import com.wap.wapp.core.model.survey.QuestionType
import com.wap.wapp.feature.management.survey.registration.SurveyFormRegistrationViewModel
import java.time.LocalDate
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SurveyFormContent(
    surveyRegistrationState: SurveyFormState,
    eventsState: SurveyFormRegistrationViewModel.EventsState,
    eventSelection: Event,
    title: String,
    content: String,
    question: String,
    questionType: QuestionType,
    time: LocalTime,
    date: LocalDate,
    currentQuestionIndex: Int,
    totalQuestionSize: Int,
    timePickerState: TimePickerState,
    showDatePicker: Boolean,
    showTimePicker: Boolean,
    onDateChanged: (LocalDate) -> Unit,
    onDatePickerStateChanged: (Boolean) -> Unit,
    onTimePickerStateChanged: (Boolean) -> Unit,
    onEventListChanged: () -> Unit,
    onEventSelected: (Event) -> Unit,
    onTitleChanged: (String) -> Unit,
    onContentChanged: (String) -> Unit,
    onQuestionChanged: (String) -> Unit,
    onQuestionTypeChanged: (QuestionType) -> Unit,
    onTimeChanged: (LocalTime) -> Unit,
    onNextButtonClicked: (SurveyFormState, SurveyFormState) -> Unit, // (currentState, nextState)
    onAddQuestionButtonClicked: () -> Unit,
    onRegisterButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        when (surveyRegistrationState) {
            SurveyFormState.EVENT_SELECTION -> {
                onEventListChanged()
                SurveyEventContent(
                    eventsState = eventsState,
                    selectedEvent = eventSelection,
                    onEventSelected = onEventSelected,
                    onNextButtonClicked = {
                        onNextButtonClicked(
                            SurveyFormState.EVENT_SELECTION,
                            SurveyFormState.INFORMATION,
                        )
                    },
                )
            }

            SurveyFormState.INFORMATION -> {
                SurveyInformationContent(
                    title = title,
                    onTitleChanged = onTitleChanged,
                    content = content,
                    onContentChanged = onContentChanged,
                    onNextButtonClicked = {
                        onNextButtonClicked(
                            SurveyFormState.INFORMATION,
                            SurveyFormState.QUESTION,
                        )
                    },
                )
            }

            SurveyFormState.QUESTION -> {
                SurveyQuestionContent(
                    question = question,
                    questionType = questionType,
                    onQuestionTypeChanged = { defaultQuestionType ->
                        onQuestionTypeChanged(defaultQuestionType)
                    },
                    onQuestionChanged = onQuestionChanged,
                    onAddSurveyQuestionButtonClicked = onAddQuestionButtonClicked,
                    currentQuestionIndex = currentQuestionIndex,
                    totalQuestionIndex = totalQuestionSize,
                    onNextButtonClicked = {
                        onNextButtonClicked(
                            SurveyFormState.QUESTION,
                            SurveyFormState.DEADLINE,
                        )
                    },
                )
            }

            SurveyFormState.DEADLINE -> {
                SurveyDeadlineContent(
                    time = time,
                    date = date,
                    timePickerState = timePickerState,
                    showDatePicker = showDatePicker,
                    showTimePicker = showTimePicker,
                    onDateChanged = onDateChanged,
                    onTimePickerStateChanged = onTimePickerStateChanged,
                    onTimeChanged = onTimeChanged,
                    onRegisterButtonClicked = onRegisterButtonClicked,
                    onDatePickerStateChanged = onDatePickerStateChanged,
                )
            }
        }
    }
}
