package com.wap.designsystem.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wap.designsystem.WappTheme

@Composable
fun WappCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = modifier,
        backgroundColor = WappTheme.colors.black25,
        content = content,
    )
}
