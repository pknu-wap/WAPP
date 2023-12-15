package com.wap.wapp.feature.survey.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navOptions
import com.wap.wapp.feature.survey.SurveyScreen
import com.wap.wapp.feature.survey.answer.SurveyAnswerScreen

fun NavController.navigateToSurvey(navOptions: NavOptions? = navOptions {}) {
    this.navigate(SurveyRoute.route, navOptions)
}

fun NavController.navigateToSurveyAnswer(eventId: Int, navOptions: NavOptions? = navOptions {}) {
    navigate(SurveyRoute.answerRoute(eventId.toString()), navOptions)
}

fun NavGraphBuilder.surveyNavGraph(
    navigateToSurveyAnswer: (Int) -> Unit,
    navigateToSurvey: () -> Unit,
) {
    composable(route = SurveyRoute.route) {
        SurveyScreen(
            viewModel = hiltViewModel(),
            selectedSurveyForm = { eventId ->
                navigateToSurveyAnswer(eventId)
            },
        )
    }

    composable(
        route = SurveyRoute.answerRoute("{id}"),
        arguments = listOf(
            navArgument("id") {
                type = NavType.StringType
            },
        ),
    ) { navBackStackEntry ->
        val eventId = navBackStackEntry.arguments?.getString("id") ?: ""
        SurveyAnswerScreen(
            viewModel = hiltViewModel(),
            onSubmitButtonClicked = navigateToSurvey,
            onBackButtonClicked = navigateToSurvey,
            eventId = eventId.toInt(),
        )
    }
}

object SurveyRoute {
    const val route: String = "survey"

    fun answerRoute(eventId: String): String = "$route/$eventId"
}
