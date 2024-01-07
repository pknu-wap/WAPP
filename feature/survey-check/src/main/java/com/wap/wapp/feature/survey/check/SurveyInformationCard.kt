package com.wap.wapp.feature.survey.check

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wap.designsystem.WappTheme

@Composable
internal fun SurveyInformationCard(
    title: String,
    content: String,
    userName: String,
    eventName: String,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = WappTheme.colors.black25,
        ),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Column {
                Text(
                    text = title,
                    style = WappTheme.typography.titleBold,
                    fontSize = 22.sp,
                    color = WappTheme.colors.white,
                    textAlign = TextAlign.Start,
                )
                Divider()
            }

            Text(
                text = content,
                style = WappTheme.typography.contentRegular,
                color = WappTheme.colors.white,
                textAlign = TextAlign.Start,
            )

            SurveyInformationContent(
                title = stringResource(R.string.event),
                content = eventName,
            )

            SurveyInformationContent(
                title = stringResource(R.string.name),
                content = userName,
            )
        }
    }
}

@Composable
private fun SurveyInformationContent(
    title: String,
    content: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            style = WappTheme.typography.contentBold,
            color = WappTheme.colors.white,
            textAlign = TextAlign.Start,
            modifier = Modifier.weight(1f),
        )

        Card(
            colors = CardDefaults.cardColors(
                containerColor = WappTheme.colors.black42,
            ),
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = content,
                style = WappTheme.typography.contentBold,
                color = WappTheme.colors.white,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            )
        }
    }
}
