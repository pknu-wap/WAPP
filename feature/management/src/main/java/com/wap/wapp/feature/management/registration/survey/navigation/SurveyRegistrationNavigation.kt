package com.wap.wapp.feature.management.registration.survey.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.wap.wapp.feature.management.registration.survey.SurveyRegistrationRoute

const val surveyRegistrationNavigationRoute = "survey_registration_route"

fun NavController.navigateToSurveyRegistration(navOptions: NavOptions? = navOptions {}) {
    this.navigate(surveyRegistrationNavigationRoute, navOptions)
}

fun NavGraphBuilder.surveyRegistrationScreen(
    navigateToManagement: () -> Unit,
) {
    composable(route = surveyRegistrationNavigationRoute) {
        SurveyRegistrationRoute(
            navigateToManagement = navigateToManagement,
        )
    }
}
