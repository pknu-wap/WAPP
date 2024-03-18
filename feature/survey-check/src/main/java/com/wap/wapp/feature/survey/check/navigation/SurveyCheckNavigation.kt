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
    backStack: SurveyDetailBackStack = SurveyDetailBackStack.SURVEY_CHECK,
    navOptions: NavOptions? = navOptions {},
) = this.navigate(SurveyCheckRoute.surveyDetailRoute(surveyId, backStack.name), navOptions)

fun NavController.navigateToSurveyCheck(navOptions: NavOptions? = navOptions {}) =
    this.navigate(SurveyCheckRoute.surveyCheckRoute, navOptions)

fun NavGraphBuilder.surveyCheckNavGraph(
    navigateToSurveyDetail: (String) -> Unit,
    navigateToSurveyCheck: () -> Unit,
    navigateToSurvey: () -> Unit,
    navigateToProfile: () -> Unit,
) {
    composable(route = SurveyCheckRoute.surveyCheckRoute) {
        SurveyCheckScreen(
            navigateToSurveyDetail = navigateToSurveyDetail,
            navigateToSurvey = navigateToSurvey,
        )
    }

    composable(
        route = SurveyCheckRoute.surveyDetailRoute("{id}", "{backStack}"),
        arguments = listOf(
            navArgument("id") {
                type = NavType.StringType
            },
            navArgument("backStack") {
                type = NavType.StringType
            },
        ),
    ) { navBackStackEntry ->
        val surveyId = navBackStackEntry.arguments?.getString("id") ?: ""
        val backStack = navBackStackEntry.arguments?.getString("backStack") ?: "SURVEY_CHECK"
        SurveyDetailRoute(
            surveyId = surveyId,
            backStack = backStack,
            navigateToSurveyCheck = navigateToSurveyCheck,
            navigateToProfile = navigateToProfile,
        )
    }
}

object SurveyCheckRoute {
    const val surveyCheckRoute = "survey/check"
    fun surveyDetailRoute(surveyId: String, backStack: String) =
        "survey/detail/$surveyId/$backStack"
}

enum class SurveyDetailBackStack {
    SURVEY_CHECK, PROFILE
}
