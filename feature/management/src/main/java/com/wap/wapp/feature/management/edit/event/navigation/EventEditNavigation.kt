package com.wap.wapp.feature.management.edit.event.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navOptions
import com.wap.wapp.feature.management.edit.event.EventEditRoute

const val eventEditNavigationRoute = "event/edit/{eventId}"

fun NavController.navigateToEventEdit(
    eventId: String,
    navOptions: NavOptions? = navOptions {},
) {
    this.navigate("event/edit/$eventId", navOptions)
}

fun NavGraphBuilder.eventEditScreen(
    navigateToManagement: () -> Unit,
) {
    composable(
        route = eventEditNavigationRoute,
        arguments = listOf(
            navArgument("eventId") {
                type = NavType.StringType
            },
        ),
    ) { navBackStackEntry ->
        val eventId = navBackStackEntry.arguments?.getString("eventId") ?: ""
        EventEditRoute(
            eventId = eventId,
            navigateToManagement = navigateToManagement,
        )
    }
}
