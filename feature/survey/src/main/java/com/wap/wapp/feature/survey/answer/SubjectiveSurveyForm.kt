package com.wap.wapp.feature.survey.answer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wap.designsystem.WappTheme
import com.wap.designsystem.component.WappRoundedTextField
import com.wap.wapp.feature.survey.R

@Composable
internal fun SubjectiveSurveyForm(
    questionTitle: String,
    answer: String,
    onAnswerChanged: (String) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
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
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            WappRoundedTextField(
                value = answer,
                onValueChange = onAnswerChanged,
                placeholder = R.string.subjective_answer_hint,
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth(),
            )

            val textCount = answer.length
            TextCounter(textCount = textCount)
        }
    }
}

@Composable
private fun TextCounter(
    textCount: Int,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = textCount.toString(),
            color = WappTheme.colors.yellow34,
            style = WappTheme.typography.labelMedium,
        )

        Text(
            text = stringResource(R.string.text_counter_content),
            color = WappTheme.colors.white,
            style = WappTheme.typography.labelMedium,
        )
    }
}
