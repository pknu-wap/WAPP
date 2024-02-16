package com.wap.wapp.feature.survey.check

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wap.designsystem.WappTheme
import com.wap.wapp.core.model.survey.Survey

@Composable
internal fun SurveyItemCard(
    onCardClicked: (String) -> Unit,
    survey: Survey,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = WappTheme.colors.black25,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCardClicked(survey.surveyId) },
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(horizontal = 16.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = survey.title,
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    color = WappTheme.colors.white,
                    style = WappTheme.typography.titleBold,
                )

                Text(
                    text = survey.eventName,
                    color = WappTheme.colors.grayA2,
                    style = WappTheme.typography.captionMedium,
                )
            }

            Text(
                text = survey.userName,
                color = WappTheme.colors.grayBD,
                style = WappTheme.typography.contentMedium,
            )
        }
    }
}
