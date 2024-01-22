package com.wap.wapp.feature.survey.answer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.wap.designsystem.WappTheme
import com.wap.designsystem.component.WappButton
import com.wap.designsystem.component.WappTitle
import com.wap.wapp.core.commmon.util.DateUtil
import com.wap.wapp.core.model.survey.SurveyForm
import com.wap.wapp.feature.survey.R

@Composable
internal fun SurveyOverview(
    surveyForm: SurveyForm,
    modifier: Modifier,
    eventName: String,
    onStartSurveyButtonClicked: () -> Unit,
) {
    Column(modifier = modifier) {
        WappTitle(
            title = surveyForm.title,
            content = surveyForm.content,
        )

        Column(
            modifier = Modifier
                .padding(top = 40.dp)
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            SurveyOverviewText(category = stringResource(R.string.event), content = eventName)

            SurveyOverviewText(
                category = stringResource(R.string.deadline),
                content = surveyForm.deadline.format(DateUtil.yyyyMMddFormatter),
            )

            SurveyOverviewText(
                category = stringResource(R.string.questionTotalNumber),
                content = surveyForm.surveyQuestionList.size.toString(),
            )
        }

        WappButton(
            textRes = R.string.start_survey,
            onClick = onStartSurveyButtonClicked,
        )
    }
}

@Composable
private fun SurveyOverviewText(category: String, content: String) {
    Text(
        text = buildAnnotatedString {
            withStyle(
                SpanStyle(color = WappTheme.colors.white),
            ) {
                append("$category : ")
            }
            withStyle(
                SpanStyle(color = WappTheme.colors.yellow34),
            ) {
                append(content)
            }
        },
        style = WappTheme.typography.titleBold,
    )
}
