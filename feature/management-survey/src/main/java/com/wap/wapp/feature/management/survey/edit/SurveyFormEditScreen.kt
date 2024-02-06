package com.wap.wapp.feature.management.survey.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.wap.designsystem.WappTheme
import com.wap.designsystem.component.WappSubTopBar
import com.wap.wapp.core.commmon.extensions.toSupportingText
import com.wap.wapp.feature.management.survey.R
import com.wap.wapp.feature.management.survey.SurveyFormContent
import com.wap.wapp.feature.management.survey.SurveyFormState
import com.wap.wapp.feature.management.survey.SurveyFormStateIndicator
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SurveyFormEditScreen(
    viewModel: SurveyFormEditViewModel = hiltViewModel(),
    surveyFormId: String,
    navigateToManagement: () -> Unit,
) {
    val currentRegistrationState = viewModel.currentSurveyFormState.collectAsState().value
    val eventList = viewModel.eventList.collectAsState().value
    val eventSelection = viewModel.surveyEventSelection.collectAsState().value
    val title = viewModel.surveyTitle.collectAsState().value
    val content = viewModel.surveyContent.collectAsState().value
    val question = viewModel.surveyQuestion.collectAsState().value
    val questionType = viewModel.surveyQuestionType.collectAsState().value
    val totalQuestionSize = viewModel.surveyQuestionList.collectAsState().value.size + 1
    val time = viewModel.surveyTimeDeadline.collectAsState().value
    val date = viewModel.surveyDateDeadline.collectAsState().value
    val snackBarHostState = remember { SnackbarHostState() }
    val timePickerState = rememberTimePickerState()
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var showDeleteSurveyDialog by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        viewModel.getSurveyForm(surveyFormId)
    }

    LaunchedEffect(true) {
        viewModel.surveyFormEditUiEvent.collectLatest { surveyFormEditEvent ->
            when (surveyFormEditEvent) {
                is SurveyFormEditViewModel.SurveyFormEditUiEvent.EditSuccess ->
                    navigateToManagement()

                is SurveyFormEditViewModel.SurveyFormEditUiEvent.DeleteSuccess ->
                    navigateToManagement()

                is SurveyFormEditViewModel.SurveyFormEditUiEvent.Failure ->
                    snackBarHostState.showSnackbar(surveyFormEditEvent.throwable.toSupportingText())

                is SurveyFormEditViewModel.SurveyFormEditUiEvent.ValidationError ->
                    snackBarHostState.showSnackbar(surveyFormEditEvent.message)
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        containerColor = WappTheme.colors.backgroundBlack,
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0.dp),
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // paddingValue padding
                .padding(vertical = 16.dp), // dp value padding
            verticalArrangement = Arrangement.spacedBy(32.dp),
        ) {
            if (showDeleteSurveyDialog) {
                DeleteSurveyDialog(
                    deleteSurvey = viewModel::deleteSurveyForm,
                    onDismissRequest = { showDeleteSurveyDialog = false },
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                WappSubTopBar(
                    titleRes = R.string.survey_edit,
                    showLeftButton = true,
                    showRightButton = true,
                    onClickLeftButton = navigateToManagement,
                    onClickRightButton = { showDeleteSurveyDialog = true },
                )

                SurveyFormStateIndicator(
                    surveyRegistrationState = currentRegistrationState,
                    modifier = Modifier.padding(horizontal = 20.dp),
                )
            }

            SurveyFormContent(
                surveyRegistrationState = currentRegistrationState,
                eventsState = eventList,
                eventSelection = eventSelection,
                title = title,
                content = content,
                question = question,
                questionType = questionType,
                date = date,
                time = time,
                timePickerState = timePickerState,
                showDatePicker = showDatePicker,
                showTimePicker = showTimePicker,
                currentQuestionIndex = totalQuestionSize,
                totalQuestionSize = totalQuestionSize,
                modifier = Modifier.padding(horizontal = 20.dp),
                onDatePickerStateChanged = { state -> showDatePicker = state },
                onTimePickerStateChanged = { state -> showTimePicker = state },
                onEventListChanged = { viewModel.getEventList() },
                onEventSelected = { event -> viewModel.setSurveyEventSelection(event) },
                onTitleChanged = { title -> viewModel.setSurveyTitle(title) },
                onContentChanged = { content -> viewModel.setSurveyContent(content) },
                onQuestionChanged = { question -> viewModel.setSurveyQuestion(question) },
                onQuestionTypeChanged = { questionType ->
                    viewModel.setSurveyQuestionType(questionType)
                },
                onDateChanged = viewModel::setSurveyDateDeadline,
                onTimeChanged = { localTime -> viewModel.setSurveyTimeDeadline(localTime) },
                onNextButtonClicked = { currentState, nextState ->
                    if (viewModel.validateSurveyForm(currentState).not()) { // 유효성 검증
                        return@SurveyFormContent
                    }

                    if (currentState == SurveyFormState.QUESTION) {
                        viewModel.addSurveyQuestion() // 마지막으로 작성한 질문, 질문 목록에 추가
                    }

                    viewModel.setSurveyFormState(nextState) // 다음 단계
                },
                onAddQuestionButtonClicked = {
                    if (viewModel.validateSurveyForm(SurveyFormState.QUESTION)) {
                        viewModel.addSurveyQuestion()
                    }
                },
                onRegisterButtonClicked = {
                    if (viewModel.validateSurveyForm(SurveyFormState.DEADLINE)) {
                        viewModel.updateSurveyForm()
                    }
                },
            )
        }
    }
}

@Composable
private fun DeleteSurveyDialog(
    deleteSurvey: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
        ),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .wrapContentSize()
                .padding(horizontal = 30.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(WappTheme.colors.black25),
        ) {
            Text(
                text = stringResource(id = R.string.delete_survey),
                style = WappTheme.typography.contentBold.copy(fontSize = 20.sp),
                color = WappTheme.colors.yellow34,
                modifier = Modifier.padding(top = 16.dp),
            )

            Divider(
                color = WappTheme.colors.gray82,
                modifier = Modifier.padding(horizontal = 12.dp),
            )

            Text(
                text = generateDialogContentString(),
                style = WappTheme.typography.contentRegular,
                color = WappTheme.colors.white,
                modifier = Modifier.padding(top = 12.dp, start = 12.dp, end = 12.dp),
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 16.dp),
            ) {
                Button(
                    onClick = {
                        deleteSurvey()
                        onDismissRequest()
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WappTheme.colors.yellow34,
                    ),
                    contentPadding = PaddingValues(vertical = 12.dp),
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        text = stringResource(id = R.string.complete),
                        style = WappTheme.typography.titleRegular,
                        color = WappTheme.colors.black,
                    )
                }

                Button(
                    onClick = onDismissRequest,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WappTheme.colors.black25,
                    ),
                    contentPadding = PaddingValues(vertical = 12.dp),
                    modifier = Modifier
                        .weight(1f)
                        .border(
                            width = 1.dp,
                            color = WappTheme.colors.yellow34,
                            shape = RoundedCornerShape(8.dp),
                        ),
                ) {
                    Text(
                        text = stringResource(R.string.cancel),
                        style = WappTheme.typography.titleRegular,
                        color = WappTheme.colors.yellow34,
                    )
                }
            }
        }
    }
}

@Composable
private fun generateDialogContentString() = buildAnnotatedString {
    append("정말로 해당 설문을 삭제하시겠습니까?\n")
    withStyle(
        style = SpanStyle(
            textDecoration = TextDecoration.Underline,
            color = WappTheme.colors.yellow34,
        ),
    ) {
        append("해당 설문과 관련된 답변들이 모두 삭제됩니다.")
    }
}
