package com.wap.wapp.feature.survey.check.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navOptions
import com.wap.wapp.feature.survey.check.SurveyCheckScreen
import com.wap.wapp.feature.survey.check.detail.SurveyDetailRoute

fun NavController.navigateToSurveyDetail(
    surveyId: String,
    navOptions: NavOptions? = navOptions {},
) = this.navigate(SurveyCheckRoute.surveyDetailRoute(surveyId), navOptions)

fun NavController.navigateToSurveyCheck(navOptions: NavOptions? = navOptions {}) =
    this.navigate(SurveyCheckRoute.surveyCheckRoute, navOptions)

fun NavGraphBuilder.surveyCheckNavGraph(
    navigateToSurveyDetail: (String) -> Unit,
    navigateToSurveyCheck: () -> Unit,
    navigateToSurvey: () -> Unit,
) {
    composable(route = SurveyCheckRoute.surveyCheckRoute) {
        SurveyCheckScreen(
            navigateToSurveyDetail = navigateToSurveyDetail,
            navigateToSurvey = navigateToSurvey,
        )
    }

    composable(
        route = SurveyCheckRoute.surveyDetailRoute("{id}"),
        arguments = listOf(
            navArgument("id") {
                type = NavType.StringType
            },
        ),
    ) { navBackStackEntry ->
        val surveyId = navBackStackEntry.arguments?.getString("id") ?: ""
        SurveyDetailRoute(
            navigateToSurveyCheck = navigateToSurveyCheck,
            surveyId = surveyId,
        )
    }
}

object SurveyCheckRoute {
    const val surveyCheckRoute = "survey/check"
    fun surveyDetailRoute(surveyId: String) = "survey/detail/$surveyId"
}
