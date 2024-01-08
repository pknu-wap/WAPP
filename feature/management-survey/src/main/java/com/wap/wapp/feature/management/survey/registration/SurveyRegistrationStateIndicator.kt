package com.wap.wapp.feature.management.survey.registration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.wap.designsystem.WappTheme
import com.wap.wapp.feature.management.survey.R

@Composable
internal fun SurveyRegistrationStateIndicator(
    surveyRegistrationState: SurveyRegistrationState,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        SurveyRegistrationStateProgressBar(surveyRegistrationState.progress)
        SurveyRegistrationStateText(surveyRegistrationState.page)
    }
}

@Composable
private fun SurveyRegistrationStateText(
    currentRegistrationPage: String,
) {
    Row {
        Text(
            text = currentRegistrationPage,
            style = WappTheme.typography.contentMedium,
            color = WappTheme.colors.yellow34,
        )
        Text(
            text = stringResource(R.string.survey_registration_total_page),
            style = WappTheme.typography.contentMedium,
            color = WappTheme.colors.white,
        )
    }
}

@Composable
private fun SurveyRegistrationStateProgressBar(
    currentRegistrationProgress: Float,
) {
    LinearProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp),
        color = WappTheme.colors.yellow34,
        trackColor = WappTheme.colors.white,
        progress = currentRegistrationProgress,
        strokeCap = StrokeCap.Round,
    )
}
