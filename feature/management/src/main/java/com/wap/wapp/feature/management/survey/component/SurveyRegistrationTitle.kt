package com.wap.wapp.feature.management.survey.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wap.designsystem.WappTheme

@Composable
internal fun SurveyRegistrationTitle(
    title: String,
    content: String,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            text = title,
            style = WappTheme.typography.titleBold,
            fontSize = 22.sp,
            color = WappTheme.colors.white,
            textAlign = TextAlign.Start,
        )

        Text(
            text = content,
            style = WappTheme.typography.contentRegular,
            color = WappTheme.colors.white,
            textAlign = TextAlign.Start,
        )
    }
}
