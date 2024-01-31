package com.wap.wapp.feature.management.survey

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
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
    timeDeadline: LocalTime,
    dateDeadline: LocalDate,
    currentQuestionNumber: Int,
    totalQuestionNumber: Int,
    timePickerState: TimePickerState,
    showDatePicker: Boolean,
    showTimePicker: Boolean,
    onDateChanged: (LocalDate) -> Unit,
    onDatePickerStateChanged: (Boolean) -> Unit,
    onTimePickerStateChanged: (Boolean) -> Unit,
    onEventContentInitialized: () -> Unit,
    onEventSelected: (Event) -> Unit,
    onTitleChanged: (String) -> Unit,
    onContentChanged: (String) -> Unit,
    onQuestionChanged: (String) -> Unit,
    onQuestionTypeChanged: (QuestionType) -> Unit,
    onPreviousQuestionButtonClicked: () -> Unit,
    onNextQuestionButtonClicked: () -> Unit,
    onTimeChanged: (LocalTime) -> Unit,
    onPreviousButtonClicked: (SurveyFormState) -> Unit, // previousState
    onNextButtonClicked: (SurveyFormState, SurveyFormState) -> Unit, // (currentState, nextState)
    onAddQuestionButtonClicked: () -> Unit,
    onRegisterButtonClicked: () -> Unit,
) {
    when (surveyRegistrationState) {
        SurveyFormState.EVENT_SELECTION -> {
            onEventContentInitialized()
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
                onPreviousButtonClicked = {
                    onPreviousButtonClicked(SurveyFormState.EVENT_SELECTION)
                },
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
                currentQuestionNumber = currentQuestionNumber,
                totalQuestionNumber = totalQuestionNumber,
                onPreviousButtonClicked = {
                    onPreviousButtonClicked(SurveyFormState.INFORMATION)
                },
                onNextButtonClicked = {
                    onNextButtonClicked(
                        SurveyFormState.QUESTION,
                        SurveyFormState.DEADLINE,
                    )
                },
                onPreviousQuestionButtonClicked = onPreviousQuestionButtonClicked,
                onNextQuestionButtonClicked = onNextQuestionButtonClicked,
            )
        }

        SurveyFormState.DEADLINE -> {
            SurveyDeadlineContent(
                timeDeadline = timeDeadline,
                dateDeadline = dateDeadline,
                timePickerState = timePickerState,
                showDatePicker = showDatePicker,
                showTimePicker = showTimePicker,
                onDateChanged = onDateChanged,
                onTimePickerStateChanged = onTimePickerStateChanged,
                onTimeChanged = onTimeChanged,
                onRegisterButtonClicked = onRegisterButtonClicked,
                onDatePickerStateChanged = onDatePickerStateChanged,
                onPreviousButtonClicked = {
                    onPreviousButtonClicked(SurveyFormState.QUESTION)
                },
            )
        }
    }
}
