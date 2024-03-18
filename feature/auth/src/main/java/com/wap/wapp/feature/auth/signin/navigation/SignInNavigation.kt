package com.wap.wapp.feature.auth.signin.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.wap.wapp.core.domain.usecase.auth.SignInUseCase
import com.wap.wapp.feature.auth.signin.SignInRoute

const val signInNavigationRoute = "signIn_route"

fun NavController.navigateToSignIn(navOptions: NavOptions? = navOptions {}) {
    this.navigate(signInNavigationRoute, navOptions)
}

fun NavGraphBuilder.signInScreen(
    signInUseCase: SignInUseCase,
    navigateToSignUp: () -> Unit,
    navigateToNotice: () -> Unit,
) {
    composable(route = signInNavigationRoute) {
        SignInRoute(
            signInUseCase = signInUseCase,
            navigateToSignUp = navigateToSignUp,
            navigateToNotice = navigateToNotice,
        )
    }
}
