package com.wap.wapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions

@Composable
fun WAPPNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = "auth_route",
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {

    }
}
