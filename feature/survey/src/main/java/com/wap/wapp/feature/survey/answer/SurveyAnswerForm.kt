package com.wap.wapp.feature.survey.answer

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.wap.designsystem.component.WappButton
import com.wap.designsystem.modifier.addFocusCleaner
import com.wap.wapp.core.model.survey.QuestionType
import com.wap.wapp.core.model.survey.Rating
import com.wap.wapp.core.model.survey.SurveyForm
import com.wap.wapp.feature.survey.R

@Composable
internal fun SurveyAnswerForm(
    surveyForm: SurveyForm,
    modifier: Modifier,
    questionNumber: Int,
    subjectiveAnswer: String,
    objectiveAnswer: Rating,
    onSubjectiveAnswerChanged: (String) -> Unit,
    onObjectiveAnswerSelected: (Rating) -> Unit,
    onNextQuestionButtonClicked: () -> Unit,
    onPreviousQuestionButtonClicked: () -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .addFocusCleaner(focusManager),
    ) {
        val surveyQuestion = surveyForm.surveyQuestionList[questionNumber]
        val lastQuestionNumber = surveyForm.surveyQuestionList.lastIndex

        SurveyAnswerStateIndicator(
            index = questionNumber + 1,
            size = lastQuestionNumber + 1,
        )

        AnimatedContent(
            targetState = questionNumber,
            transitionSpec = {
                if (targetState > initialState) {
                    slideInHorizontally(initialOffsetX = { it }) + fadeIn() togetherWith
                        slideOutHorizontally(targetOffsetX = { -it }) + fadeOut()
                } else {
                    slideInHorizontally(initialOffsetX = { -it }) + fadeIn() togetherWith
                        slideOutHorizontally(targetOffsetX = { it }) + fadeOut()
                }
            },
            label = stringResource(R.string.survey_answer_form_animated_content_label),
        ) { questionNumber ->
            Column {
                when (surveyForm.surveyQuestionList[questionNumber].questionType) {
                    QuestionType.SUBJECTIVE -> {
                        SubjectiveSurveyForm(
                            questionTitle = surveyQuestion.questionTitle,
                            answer = subjectiveAnswer,
                            onAnswerChanged = onSubjectiveAnswerChanged,
                            modifier = Modifier.weight(1f),
                        )
                    }

                    QuestionType.OBJECTIVE -> {
                        ObjectiveSurveyForm(
                            questionTitle = surveyQuestion.questionTitle,
                            answer = objectiveAnswer,
                            onAnswerSelected = onObjectiveAnswerSelected,
                            modifier = Modifier.weight(1f),
                        )
                    }
                }

                val isGreaterThanFirstQuestion = questionNumber > 0
                val isLastQuestion = questionNumber == lastQuestionNumber // 마지막 응답일 경우, 완료로 변경
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    WappButton(
                        textRes = R.string.previous,
                        onClick = onPreviousQuestionButtonClicked,
                        isEnabled = isGreaterThanFirstQuestion,
                        modifier = Modifier.weight(1f),
                    )

                    WappButton(
                        textRes = if (isLastQuestion) R.string.submit else R.string.next,
                        onClick = { onNextQuestionButtonClicked() },
                        isEnabled = checkQuestionTypeAndSubjectiveAnswer(
                            questionType = surveyQuestion.questionType,
                            subjectiveAnswer = subjectiveAnswer,
                        ),
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }
    }
}

private fun checkQuestionTypeAndSubjectiveAnswer(
    questionType: QuestionType,
    subjectiveAnswer: String,
): Boolean {
    if (questionType == QuestionType.OBJECTIVE) {
        return true
    }
    return subjectiveAnswer.length >= MINIMUM_ANSWER_LENGTH
}

private const val MINIMUM_ANSWER_LENGTH = 8
