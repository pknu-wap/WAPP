package com.wap.wapp.feature.survey.answer

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wap.designsystem.WappTheme
import com.wap.wapp.core.model.survey.Rating
import com.wap.wapp.core.model.survey.toDescription

@Composable
internal fun ObjectiveSurveyForm(
    questionTitle: String,
    answer: Rating,
    onAnswerSelected: (Rating) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(32.dp),
    ) {
        Text(
            text = questionTitle,
            style = WappTheme.typography.titleRegular,
            color = WappTheme.colors.white,
            fontSize = 22.sp,
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            ObjectiveAnswerCard(
                rating = Rating.GOOD,
                selectedRating = answer,
                onCardSelected = onAnswerSelected,
            )

            ObjectiveAnswerCard(
                rating = Rating.MEDIOCRE,
                selectedRating = answer,
                onCardSelected = onAnswerSelected,
            )

            ObjectiveAnswerCard(
                rating = Rating.BAD,
                selectedRating = answer,
                onCardSelected = onAnswerSelected,
            )
        }
    }
}

@Composable
private fun ObjectiveAnswerCard(
    rating: Rating,
    selectedRating: Rating,
    onCardSelected: (Rating) -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = WappTheme.colors.black25),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCardSelected(rating) },
        border = BorderStroke(
            color = if (rating == selectedRating) {
                WappTheme.colors.yellow34
            } else {
                WappTheme.colors.black25
            },
            width = 1.dp,
        ),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = rating.toDescription().title,
                color = WappTheme.colors.white,
                style = WappTheme.typography.contentBold,
                textAlign = TextAlign.Center,
            )

            Text(
                text = rating.toDescription().content,
                color = WappTheme.colors.white,
                style = WappTheme.typography.labelRegular,
                modifier = Modifier.weight(4f),
                textAlign = TextAlign.End,
            )
        }
    }
}
