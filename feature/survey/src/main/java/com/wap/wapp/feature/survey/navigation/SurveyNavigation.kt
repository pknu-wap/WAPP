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

fun NavController.navigateToSurveyAnswer(
    surveyFormId: String,
    navOptions: NavOptions? = navOptions {},
) {
    navigate(SurveyRoute.answerRoute(surveyFormId), navOptions)
}

fun NavGraphBuilder.surveyNavGraph(
    navigateToSurveyAnswer: (String) -> Unit,
    navigateToSurvey: () -> Unit,
    navigateToSignIn: () -> Unit,
    navigateToSurveyCheck: () -> Unit,
) {
    composable(route = SurveyRoute.route) {
        SurveyScreen(
            viewModel = hiltViewModel(),
            navigateToSignIn = navigateToSignIn,
            navigateToSurveyAnswer = { surveyFormId ->
                navigateToSurveyAnswer(surveyFormId)
            },
            navigateToSurveyCheck = navigateToSurveyCheck,
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
        val surveyFormId = navBackStackEntry.arguments?.getString("id") ?: ""
        SurveyAnswerScreen(
            viewModel = hiltViewModel(),
            onSubmitButtonClicked = navigateToSurvey,
            onBackButtonClicked = navigateToSurvey,
            surveyFormId = surveyFormId,
        )
    }
}

object SurveyRoute {
    const val route: String = "survey"

    fun answerRoute(surveyFormId: String): String = "$route/$surveyFormId"
}
