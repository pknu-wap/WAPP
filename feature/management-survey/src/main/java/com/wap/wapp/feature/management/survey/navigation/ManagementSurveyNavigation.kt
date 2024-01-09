package com.wap.wapp.feature.management.survey.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navOptions
import com.wap.wapp.feature.management.survey.edit.SurveyFormEditScreen
import com.wap.wapp.feature.management.survey.registration.SurveyRegistrationScreen

fun NavController.navigateToSurveyFormRegistration(navOptions: NavOptions? = navOptions {}) {
    this.navigate(ManagementSurveyRoute.surveyFormRegistrationRoute, navOptions)
}

fun NavController.navigateToSurveyFormEdit(
    surveyFormId: String,
    navOptions: NavOptions? = navOptions {},
) {
    this.navigate(ManagementSurveyRoute.surveyFormEditRoute(surveyFormId), navOptions)
}

fun NavGraphBuilder.managementSurveyNavGraph(
    navigateToManagement: () -> Unit,
) {
    composable(route = ManagementSurveyRoute.surveyFormRegistrationRoute) {
        SurveyRegistrationScreen(
            navigateToManagement = navigateToManagement,
        )
    }

    composable(
        route = ManagementSurveyRoute.surveyFormEditRoute("{id}"),
        arguments = listOf(
            navArgument("id") {
                type = NavType.StringType
            },
        ),
    ) { navBackStackEntry ->
        val surveyFormId = navBackStackEntry.arguments?.getString("id") ?: ""
        SurveyFormEditScreen(
            surveyFormId = surveyFormId,
            navigateToManagement = navigateToManagement,
        )
    }
}

object ManagementSurveyRoute {
    const val surveyFormRegistrationRoute = "surveyForm/registration"

    fun surveyFormEditRoute(surveyFormId: String) = "surveyForm/edit/$surveyFormId"
}
