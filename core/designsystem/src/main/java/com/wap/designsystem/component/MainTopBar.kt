package com.wap.designsystem.component

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable

@Composable
fun WappMainTopBar(
    @StringRes titleRes: Int,
    @StringRes contentRes: Int,
    showSettingButton: Boolean = false,
    onClickSettingButton: () -> Unit = {},
) {}
