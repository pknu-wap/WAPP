package com.wap.wapp.feature.management.check.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.wap.wapp.feature.management.check.SurveyCheckRoute

const val surveyCheckNavigationRoute = "survey_check_route"

fun NavController.navigateToSurveyCheck(navOptions: NavOptions? = navOptions {}) {
    this.navigate(surveyCheckNavigationRoute, navOptions)
}

fun NavGraphBuilder.surveyCheckScreen(
    navigateToManagement: () -> Unit,
) {
    composable(route = surveyCheckNavigationRoute) {
        SurveyCheckRoute(
            navigateToManagement = navigateToManagement,
        )
    }
}
