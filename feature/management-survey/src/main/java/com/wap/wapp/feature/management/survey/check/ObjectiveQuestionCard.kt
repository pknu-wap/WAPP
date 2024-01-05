package com.wap.wapp.feature.management.survey.check

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.wap.designsystem.WappTheme
import com.wap.wapp.core.model.survey.Rating
import com.wap.wapp.core.model.survey.SurveyAnswer
import com.wap.wapp.core.model.survey.toDescription

@Composable
internal fun ObjectiveQuestionCard(surveyAnswer: SurveyAnswer) {
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
            Text(
                text = surveyAnswer.questionTitle,
                style = WappTheme.typography.titleBold,
                color = WappTheme.colors.white,
                textAlign = TextAlign.Start,
            )

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Rating.values().forEach { rating ->
                    if (rating.toString() == surveyAnswer.questionAnswer) {
                        ObjectiveAnswerIndicator(rating = rating, selected = true)
                    } else {
                        ObjectiveAnswerIndicator(rating = rating, selected = false)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ObjectiveAnswerIndicator(
    rating: Rating,
    selected: Boolean,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = rating.toDescription().title,
            style = WappTheme.typography.captionRegular,
            color = WappTheme.colors.white,
        )

        FilterChip(
            selected = selected,
            onClick = { },
            label = { },
            colors = FilterChipDefaults.filterChipColors(
                containerColor = WappTheme.colors.black42,
                selectedContainerColor = WappTheme.colors.yellow34,
            ),
            modifier = Modifier.size(height = 40.dp, width = 80.dp),
        )
    }
}
