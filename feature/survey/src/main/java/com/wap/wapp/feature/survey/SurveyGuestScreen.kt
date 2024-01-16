package com.wap.wapp.feature.survey

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.wap.designsystem.WappTheme
import com.wap.designsystem.component.WappButton

@Composable
internal fun SurveyGuestScreen(
    onButtonClicked: () -> Unit,
) {
    Surface(
        color = WappTheme.colors.backgroundBlack,
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = stringResource(R.string.survey_guset_title),
                style = WappTheme.typography.titleBold,
                color = WappTheme.colors.white,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )

            Text(
                text = stringResource(R.string.survey_guest_content),
                style = WappTheme.typography.captionMedium,
                color = WappTheme.colors.white,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.padding(vertical = 16.dp))

            WappButton(
                textRes = R.string.go_to_signin,
                onClick = onButtonClicked,
                modifier = Modifier.padding(horizontal = 32.dp),
            )
        }
    }
}
