package com.wap.wapp.feature.survey

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.wap.designsystem.WappTheme
import com.wap.wapp.core.model.survey.SurveyForm

@Composable
internal fun SurveyFormItemCard(
    surveyForm: SurveyForm,
    selectedSurveyForm: (String) -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = WappTheme.colors.black25,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { selectedSurveyForm(surveyForm.surveyFormId) },
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = surveyForm.title,
                    color = WappTheme.colors.white,
                    modifier = Modifier.weight(1f),
                    style = WappTheme.typography.titleBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = surveyForm.calculateDeadline(),
                    color = WappTheme.colors.yellow34,
                    style = WappTheme.typography.captionMedium,
                    modifier = Modifier.padding(start = 8.dp),
                )
            }
            Text(
                text = surveyForm.content,
                color = WappTheme.colors.grayBD,
                maxLines = 1,
                style = WappTheme.typography.labelMedium,
            )
        }
    }
}
