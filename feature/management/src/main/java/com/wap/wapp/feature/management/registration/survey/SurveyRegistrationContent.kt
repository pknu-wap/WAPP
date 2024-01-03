package com.wap.wapp.feature.management.registration.survey

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import com.wap.wapp.core.model.event.Event
import com.wap.wapp.core.model.survey.QuestionType
import com.wap.wapp.feature.management.registration.survey.deadline.SurveyDeadlineContent
import com.wap.wapp.feature.management.registration.survey.event.SurveyEventSelectionContent
import com.wap.wapp.feature.management.registration.survey.information.SurveyInformationContent
import com.wap.wapp.feature.management.registration.survey.question.SurveyQuestionContent
import java.time.LocalDate
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SurveyRegistrationContent(
    surveyRegistrationState: SurveyRegistrationState,
    eventList: List<Event>,
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
    onNextButtonClicked: (SurveyRegistrationState) -> Unit,
    onAddQuestionButtonClicked: () -> Unit,
    onRegisterButtonClicked: () -> Unit,
) {
    when (surveyRegistrationState) {
        SurveyRegistrationState.EVENT_SELECTION -> {
            onEventListChanged()
            SurveyEventSelectionContent(
                eventList = eventList,
                eventSelection = eventSelection,
                // default prefix -> 함수 parameter <-> 콜백 함수 parameter conflict
                onEventSelected = onEventSelected,
                onNextButtonClicked = { onNextButtonClicked(SurveyRegistrationState.INFORMATION) },
            )
        }

        SurveyRegistrationState.INFORMATION -> {
            SurveyInformationContent(
                title = title,
                onTitleChanged = onTitleChanged,
                content = content,
                onContentChanged = onContentChanged,
                onNextButtonClicked = { onNextButtonClicked(SurveyRegistrationState.QUESTION) },
            )
        }

        SurveyRegistrationState.QUESTION -> {
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
                onNextButtonClicked = { onNextButtonClicked(SurveyRegistrationState.DEADLINE) },
            )
        }

        SurveyRegistrationState.DEADLINE -> {
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
