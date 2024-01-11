package com.wap.wapp.feature.management.survey

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.wap.designsystem.WappTheme
import com.wap.designsystem.component.WappButton
import com.wap.designsystem.component.WappRoundedTextField
import com.wap.designsystem.component.WappTitle
import com.wap.wapp.core.model.survey.QuestionType

@Composable
internal fun SurveyQuestionContent(
    question: String,
    questionType: QuestionType,
    onQuestionChanged: (String) -> Unit,
    onQuestionTypeChanged: (QuestionType) -> Unit,
    onNextButtonClicked: () -> Unit,
    onAddSurveyQuestionButtonClicked: () -> Unit,
    currentQuestionIndex: Int,
    totalQuestionIndex: Int,
) {
    val scrollState = rememberScrollState()

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
    ) {
        WappTitle(
            title = stringResource(R.string.survey_question_title),
            content = stringResource(R.string.survey_question_content),
            modifier = Modifier.padding(top = 10.dp, bottom = 30.dp),
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = stringResource(R.string.survey_question),
                style = WappTheme.typography.titleBold,
                color = WappTheme.colors.white,
            )

            Text(
                color = WappTheme.colors.white,
                style = WappTheme.typography.contentRegular,
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            WappTheme.colors.yellow34,
                        ),
                    ) { append(currentQuestionIndex.toString()) }
                    append(" / $totalQuestionIndex")
                },
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        WappRoundedTextField(
            value = question,
            onValueChange = onQuestionChanged,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            placeholder = R.string.suvey_question_hint,
        )

        Text(
            text = stringResource(R.string.survey_question_type),
            style = WappTheme.typography.titleBold,
            color = WappTheme.colors.white,
            modifier = Modifier.padding(top = 10.dp),
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            SurveyQuestionTypeChip(
                selected = (questionType == QuestionType.SUBJECTIVE),
                onSelected = { onQuestionTypeChanged(QuestionType.SUBJECTIVE) },
                label = stringResource(R.string.essay),
            )

            SurveyQuestionTypeChip(
                selected = (questionType == QuestionType.OBJECTIVE),
                onSelected = { onQuestionTypeChanged(QuestionType.OBJECTIVE) },
                label = stringResource(R.string.multie_choice),
            )
        }

        SurveyQuestionTypeDescription(
            type = questionType,
        )

        Spacer(modifier = Modifier.weight(1f))

        SurveyQuestionButton(
            onAddSurveyQuestionButtonClicked = {
                onAddSurveyQuestionButtonClicked()
            },
            onNextButtonClicked = onNextButtonClicked,
            modifier = Modifier.padding(top = 10.dp, bottom = 20.dp),
        )
    }
}

@Composable
private fun SurveyQuestionTypeDescription(
    type: QuestionType,
) {
    when (type) {
        QuestionType.SUBJECTIVE -> {
            Text(
                text = stringResource(R.string.essay_hint),
                color = WappTheme.colors.yellow34,
                style = WappTheme.typography.labelRegular,
                modifier = Modifier.padding(top = 10.dp),
            )
        }

        QuestionType.OBJECTIVE -> {
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth(),
            ) {
                SurveyQuestionTypeCard(
                    title = stringResource(R.string.good),
                    content = stringResource(R.string.good_description),
                )

                SurveyQuestionTypeCard(
                    title = stringResource(R.string.soso),
                    content = stringResource(R.string.soso_description),
                )

                SurveyQuestionTypeCard(
                    title = stringResource(R.string.bad),
                    content = stringResource(R.string.bad_description),
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SurveyQuestionTypeChip(
    selected: Boolean,
    onSelected: () -> Unit,
    label: String,
) {
    FilterChip(
        selected = selected,
        onClick = { onSelected() },
        label = {
            Text(text = label, modifier = Modifier.padding(vertical = 8.dp))
        },
        colors = FilterChipDefaults.filterChipColors(
            containerColor = WappTheme.colors.backgroundBlack,
            selectedContainerColor = WappTheme.colors.backgroundBlack,
            labelColor = WappTheme.colors.white,
            selectedLabelColor = WappTheme.colors.yellow34,
        ),
        border = FilterChipDefaults.filterChipBorder(
            borderWidth = 1.dp,
            selectedBorderWidth = 1.dp,
            borderColor = WappTheme.colors.white,
            selectedBorderColor = WappTheme.colors.yellow34,
        ),
    )
}

@Composable
private fun SurveyQuestionTypeCard(
    title: String,
    content: String,
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = WappTheme.colors.black25,
        ),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = title,
                color = WappTheme.colors.white,
                style = WappTheme.typography.contentBold,
                textAlign = TextAlign.Center,
            )

            Text(
                text = content,
                color = WappTheme.colors.white,
                style = WappTheme.typography.labelRegular,
                modifier = Modifier.weight(4f),
                textAlign = TextAlign.End,
            )
        }
    }
}

@Composable
private fun SurveyQuestionButton(
    onAddSurveyQuestionButtonClicked: () -> Unit,
    onNextButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier,
    ) {
        WappButton(
            onClick = onAddSurveyQuestionButtonClicked,
            textRes = R.string.add_survey_question,
            modifier = Modifier.weight(1f),
        )

        WappButton(
            onClick = onNextButtonClicked,
            textRes = R.string.next,
            modifier = Modifier.weight(1f),
        )
    }
}
