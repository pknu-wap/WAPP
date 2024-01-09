package com.wap.wapp.feature.management.event.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navOptions
import com.wap.wapp.feature.management.event.edit.EventEditRoute
import com.wap.wapp.feature.management.event.registration.EventRegistrationRoute

const val eventRegistrationNavigationRoute = "event_registration_route"
const val eventEditNavigationRoute = "event/edit/{date}/{eventId}"

fun NavController.navigateToEventRegistration(navOptions: NavOptions? = navOptions {}) {
    this.navigate(eventRegistrationNavigationRoute, navOptions)
}

fun NavController.navigateToEventEdit(
    date: String,
    eventId: String,
    navOptions: NavOptions? = navOptions {},
) {
    this.navigate("event/edit/$date/$eventId", navOptions)
}

fun NavGraphBuilder.managementEventNavGraph(
    navigateToManagement: () -> Unit,
) {
    composable(route = eventRegistrationNavigationRoute) {
        EventRegistrationRoute(
            navigateToManagement = navigateToManagement,
        )
    }

    composable(
        route = eventEditNavigationRoute,
        arguments = listOf(
            navArgument("date") { type = NavType.StringType },
            navArgument("eventId") { type = NavType.StringType },
        ),
    ) { navBackStackEntry ->
        val date = navBackStackEntry.arguments?.getString("date") ?: ""
        val eventId = navBackStackEntry.arguments?.getString("eventId") ?: ""
        EventEditRoute(
            date = date,
            eventId = eventId,
            navigateToManagement = navigateToManagement,
        )
    }
}
