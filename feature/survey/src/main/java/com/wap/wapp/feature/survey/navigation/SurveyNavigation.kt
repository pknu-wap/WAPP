package com.wap.wapp.feature.survey.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.wap.wapp.feature.survey.SurveyRoute

const val surveyNavigationRoute = "survey_route"

fun NavController.navigateToSurvey(navOptions: NavOptions? = navOptions {}) {
    this.navigate(surveyNavigationRoute, navOptions)
}

fun NavGraphBuilder.surveyScreen() {
    composable(route = surveyNavigationRoute) {
        SurveyRoute()
    }
}
