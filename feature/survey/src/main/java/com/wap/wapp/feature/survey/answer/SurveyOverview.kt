package com.wap.wapp.feature.survey.answer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
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
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            text = category,
            color = WappTheme.colors.white,
            style = WappTheme.typography.titleBold,
        )

        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier
                .width(200.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(WappTheme.colors.black25),
        ) {
            Text(
                text = content,
                color = WappTheme.colors.white,
                style = WappTheme.typography.contentMedium,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(vertical = 10.dp, horizontal = 10.dp),
            )
        }
    }
}
