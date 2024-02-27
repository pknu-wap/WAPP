package com.wap.wapp.feature.auth.signup.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.wap.wapp.feature.auth.signup.SignUpRoute

const val signUpNavigationRoute = "signUp_route"

fun NavController.navigateToSignUp(navOptions: NavOptions? = navOptions {}) {
    this.navigate(signUpNavigationRoute, navOptions)
}

fun NavGraphBuilder.signUpScreen(
    navigateToSignIn: () -> Unit,
    navigateToNotice: () -> Unit,
) {
    composable(route = signUpNavigationRoute) {
        SignUpRoute(
            navigateToSignIn = navigateToSignIn,
            navigateToNotice = navigateToNotice,
        )
    }
}
