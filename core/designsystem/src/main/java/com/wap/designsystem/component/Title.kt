package com.wap.designsystem.component

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
fun WappTitle(
    title: String,
    content: String,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.fillMaxWidth(),
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
