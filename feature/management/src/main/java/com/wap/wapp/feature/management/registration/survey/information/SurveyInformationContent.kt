package com.wap.wapp.feature.management.registration.survey.information

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.wap.designsystem.WappTheme
import com.wap.designsystem.component.WappButton
import com.wap.designsystem.component.WappTitle
import com.wap.wapp.feature.management.R
import com.wap.wapp.feature.management.registration.component.SurveyRegistrationTextField

@Composable
internal fun SurveyInformationContent(
    title: String,
    onTitleChanged: (String) -> Unit,
    content: String,
    onContentChanged: (String) -> Unit,
    onNextButtonClicked: () -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        verticalArrangement = Arrangement.spacedBy(32.dp),
        modifier = Modifier.verticalScroll(scrollState),
    ) {
        WappTitle(
            title = stringResource(R.string.survey_information_title),
            content = stringResource(R.string.survey_information_content),
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = stringResource(R.string.survey_title),
                style = WappTheme.typography.titleBold,
                color = WappTheme.colors.white,
            )
            SurveyRegistrationTextField(
                value = title,
                onValueChange = onTitleChanged,
                placeholder = stringResource(R.string.survey_title_hint),
                modifier = Modifier.fillMaxWidth(),
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = stringResource(R.string.survey_introduce),
                style = WappTheme.typography.titleBold,
                color = WappTheme.colors.white,
            )
            SurveyRegistrationTextField(
                value = content,
                onValueChange = onContentChanged,
                placeholder = stringResource(R.string.survey_introduce_hint),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
            )
        }

        WappButton(
            onClick = onNextButtonClicked,
            textRes = R.string.next,
        )
    }
}
