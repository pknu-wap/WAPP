package com.wap.wapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.wap.wapp.core.domain.usecase.auth.SignInUseCase
import com.wap.wapp.feature.auth.signin.navigation.signInScreen
import com.wap.wapp.feature.auth.signup.navigation.navigateToSignUp
import com.wap.wapp.feature.auth.signup.navigation.signUpScreen
import com.wap.wapp.feature.management.navigation.managementScreen
import com.wap.wapp.feature.notice.navigation.navigateToNotice
import com.wap.wapp.feature.notice.navigation.noticeScreen
import com.wap.wapp.feature.profile.navigation.profileScreen
import com.wap.wapp.feature.profile.profilesetting.navigation.navigateToProfileSetting
import com.wap.wapp.feature.splash.navigation.splashNavigationRoute
import com.wap.wapp.feature.survey.navigation.surveyScreen

@Composable
fun WAPPNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    signInUseCase: SignInUseCase,
    startDestination: String = splashNavigationRoute,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        signInScreen(
            signInUseCase = signInUseCase,
            navigateToNotice = { navController.navigateToNotice() },
            navigateToSignUp = { navController.navigateToSignUp() },
        )
        signUpScreen(
            navigateToNotice = { navController.navigateToNotice() },
            navigateToSignIn = { navController.navigateToNotice() },
        )
        noticeScreen()
        surveyScreen()
        profileScreen(
            navigateToProfileSetting = { navController.navigateToProfileSetting() },
        )
        managementScreen()
    }
}
