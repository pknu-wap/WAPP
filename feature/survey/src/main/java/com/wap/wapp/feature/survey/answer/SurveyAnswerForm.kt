package com.wap.wapp.feature.survey.answer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wap.designsystem.component.WappButton
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
    Column(
        verticalArrangement = Arrangement.spacedBy(40.dp),
        modifier = modifier,
    ) {
        val surveyQuestion = surveyForm.surveyQuestionList[questionNumber]
        val lastQuestionNumber = surveyForm.surveyQuestionList.lastIndex

        SurveyAnswerStateIndicator(
            index = questionNumber + 1,
            size = lastQuestionNumber + 1,
        )

        when (surveyQuestion.questionType) {
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

private fun checkQuestionTypeAndSubjectiveAnswer(
    questionType: QuestionType,
    subjectiveAnswer: String,
): Boolean {
    if (questionType == QuestionType.OBJECTIVE) {
        return true
    }
    return subjectiveAnswer.length >= 10
}
