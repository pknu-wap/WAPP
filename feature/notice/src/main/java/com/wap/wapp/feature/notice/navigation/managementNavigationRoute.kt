package com.wap.wapp.feature.notice.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.wap.wapp.feature.notice.NoticeRoute

const val noticeNavigationRoute = "notice_route"

fun NavController.navigateToNotice(navOptions: NavOptions? = navOptions {}) {
    this.navigate(noticeNavigationRoute, navOptions)
}

fun NavGraphBuilder.noticeScreen() {
    composable(route = noticeNavigationRoute) {
        NoticeRoute()
    }
}
