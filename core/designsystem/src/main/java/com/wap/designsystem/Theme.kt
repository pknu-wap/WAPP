package com.wap.designsystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

@Composable
fun WappTheme(
    content: @Composable () -> Unit,
) {
    val colors = WappColor()
    val typography = WappTypography()
    CompositionLocalProvider(
        LocalWappColors provides colors,
        LocalWappTypography provides typography,
    ) {
        MaterialTheme(content = content)
    }
}

object WappTheme {
    val colors: WappColor @Composable get() = LocalWappColors.current
    val typography: WappTypography @Composable get() = LocalWappTypography.current
}

private val LocalWappColors = staticCompositionLocalOf<WappColor> {
    error("Any WappColors Did Not Provided")
}

private val LocalWappTypography = staticCompositionLocalOf<WappTypography> {
    error("Any WappTypography Did Not Provided")
}
