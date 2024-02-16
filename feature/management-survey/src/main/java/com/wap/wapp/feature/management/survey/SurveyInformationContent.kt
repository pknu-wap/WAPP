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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.wap.designsystem.WappTheme
import com.wap.designsystem.component.WappButton
import com.wap.designsystem.component.WappRoundedTextField
import com.wap.designsystem.component.WappTitle

@Composable
internal fun SurveyInformationContent(
    title: String,
    onTitleChanged: (String) -> Unit,
    content: String,
    onContentChanged: (String) -> Unit,
    onPreviousButtonClicked: () -> Unit,
    onNextButtonClicked: () -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
    ) {
        WappTitle(
            title = stringResource(R.string.survey_information_title),
            content = stringResource(R.string.survey_information_content),
            modifier = Modifier.padding(top = 10.dp, bottom = 24.dp),
        )

        Text(
            text = stringResource(R.string.survey_title),
            style = WappTheme.typography.titleBold,
            color = WappTheme.colors.white,
        )

        WappRoundedTextField(
            value = title,
            onValueChange = onTitleChanged,
            placeholder = R.string.survey_title_hint,
            modifier = Modifier.fillMaxWidth(),
        )

        Text(
            text = stringResource(R.string.survey_introduce),
            style = WappTheme.typography.titleBold,
            color = WappTheme.colors.white,
            modifier = Modifier.padding(top = 14.dp),
        )

        WappRoundedTextField(
            value = content,
            onValueChange = onContentChanged,
            placeholder = R.string.survey_introduce_hint,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            WappButton(
                textRes = R.string.previous,
                onClick = onPreviousButtonClicked,
                modifier = Modifier.weight(1f),
            )

            WappButton(
                textRes = R.string.next,
                onClick = onNextButtonClicked,
                modifier = Modifier.weight(1f),
            )
        }
    }
}
