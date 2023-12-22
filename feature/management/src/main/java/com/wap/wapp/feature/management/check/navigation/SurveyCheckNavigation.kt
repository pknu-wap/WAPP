package com.wap.wapp.feature.management.check.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navOptions
import com.wap.wapp.feature.management.check.SurveyCheckRoute

fun NavController.navigateToSurveyCheck(
    surveyId: String,
    navOptions: NavOptions? = navOptions {},
) {
    this.navigate("survey/check/$surveyId", navOptions)
}

fun NavGraphBuilder.surveyCheckScreen(
    navigateToManagement: () -> Unit,
) {
    composable(
        route = SurveyCheckRoute.route,
        arguments = listOf(
            navArgument("surveyId") {
                type = NavType.StringType
            },
        ),
    ) { navBackStackEntry ->
        val surveyId = navBackStackEntry.arguments?.getString("surveyId") ?: ""
        SurveyCheckRoute(
            navigateToManagement = navigateToManagement,
            surveyId = surveyId,
        )
    }
}

object SurveyCheckRoute {
    const val route = "survey/check/{surveyId}"
}
