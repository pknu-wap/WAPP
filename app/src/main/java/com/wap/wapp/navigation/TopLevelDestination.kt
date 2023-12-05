package com.wap.wapp.navigation

import androidx.annotation.StringRes

enum class TopLevelDestination(
    val route: String,
    @StringRes val iconTextId: Int,
    @StringRes val titleTextId: Int,
) {
}
