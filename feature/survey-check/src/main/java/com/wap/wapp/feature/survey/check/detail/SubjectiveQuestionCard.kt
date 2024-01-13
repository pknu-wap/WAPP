package com.wap.wapp.feature.survey.check.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.wap.designsystem.WappTheme
import com.wap.wapp.core.model.survey.SurveyAnswer

@Composable
internal fun SubjectiveQuestionCard(surveyAnswer: SurveyAnswer) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = WappTheme.colors.black25,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Text(
                text = surveyAnswer.questionTitle,
                style = WappTheme.typography.titleBold,
                color = WappTheme.colors.white,
                textAlign = TextAlign.Start,
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = surveyAnswer.questionAnswer,
                style = WappTheme.typography.contentRegular,
                color = WappTheme.colors.white,
                textAlign = TextAlign.Start,
            )
            Divider()
        }
    }
}
