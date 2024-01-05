package com.wap.wapp.navigation

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.wap.designsystem.WappTheme
import com.wap.wapp.core.domain.usecase.auth.SignInUseCase
import com.wap.wapp.feature.auth.signin.navigation.navigateToSignIn
import com.wap.wapp.feature.auth.signin.navigation.signInScreen
import com.wap.wapp.feature.auth.signup.navigation.navigateToSignUp
import com.wap.wapp.feature.auth.signup.navigation.signUpScreen
import com.wap.wapp.feature.management.survey.check.navigation.navigateToSurveyCheck
import com.wap.wapp.feature.management.survey.check.navigation.surveyCheckScreen
import com.wap.wapp.feature.management.edit.event.navigation.eventEditScreen
import com.wap.wapp.feature.management.edit.event.navigation.navigateToEventEdit
import com.wap.wapp.feature.management.navigation.managementScreen
import com.wap.wapp.feature.management.navigation.navigateToManagement
import com.wap.wapp.feature.management.registration.event.navigation.eventRegistrationScreen
import com.wap.wapp.feature.management.registration.event.navigation.navigateToEventRegistration
import com.wap.wapp.feature.management.survey.registration.navigation.navigateToSurveyRegistration
import com.wap.wapp.feature.management.survey.registration.navigation.surveyRegistrationScreen
import com.wap.wapp.feature.notice.navigation.navigateToNotice
import com.wap.wapp.feature.notice.navigation.noticeScreen
import com.wap.wapp.feature.profile.navigation.navigateToProfile
import com.wap.wapp.feature.profile.navigation.profileNavigationRoute
import com.wap.wapp.feature.profile.navigation.profileScreen
import com.wap.wapp.feature.profile.profilesetting.navigation.navigateToProfileSetting
import com.wap.wapp.feature.profile.profilesetting.navigation.profileSettingNavigationRoute
import com.wap.wapp.feature.profile.profilesetting.navigation.profileSettingScreen
import com.wap.wapp.feature.splash.navigation.splashNavigationRoute
import com.wap.wapp.feature.splash.navigation.splashScreen
import com.wap.wapp.feature.survey.navigation.navigateToSurvey
import com.wap.wapp.feature.survey.navigation.navigateToSurveyAnswer
import com.wap.wapp.feature.survey.navigation.surveyNavGraph

@Composable
fun WappNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    signInUseCase: SignInUseCase,
    startDestination: String = splashNavigationRoute,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier.background(WappTheme.colors.black25),
    ) {
        splashScreen(
            navigateToAuth = {
                navController.navigateToSignIn(
                    navOptions {
                        popUpTo(splashNavigationRoute) { inclusive = true }
                    },
                )
            },
        )
        signInScreen(
            signInUseCase = signInUseCase,
            navigateToNotice = navController::navigateToNotice,
            navigateToSignUp = navController::navigateToSignUp,
        )
        signUpScreen(
            navigateToNotice = navController::navigateToNotice,
            navigateToSignIn = navController::navigateToSignIn,
        )
        noticeScreen()
        surveyNavGraph(
            navigateToSurvey = navController::navigateToSurvey,
            navigateToSurveyAnswer = navController::navigateToSurveyAnswer,
            navigateToSignIn = navController::navigateToSignIn,
        )
        surveyCheckScreen(
            navigateToManagement = navController::navigateToManagement,
        )
        surveyRegistrationScreen(
            navigateToManagement = navController::navigateToManagement,
        )
        eventRegistrationScreen(
            navigateToManagement = navController::navigateToManagement,
        )
        eventEditScreen(
            navigateToManagement = navController::navigateToManagement,
        )
        profileScreen(
            navigateToProfileSetting = navController::navigateToProfileSetting,
            navigateToSignInScreen = {
                navController.navigateToSignIn(
                    navOptions {
                        popUpTo(profileNavigationRoute)
                    },
                )
            },
        )
        profileSettingScreen(
            navigateToProfile = {
                navController.navigateToProfile(
                    navOptions {
                        popUpTo(profileSettingNavigationRoute) { inclusive = true }
                    },
                )
            },
        )
        managementScreen(
            navigateToSurveyRegistration = navController::navigateToSurveyRegistration,
            navigateToEventRegistration = navController::navigateToEventRegistration,
            navigateToEventEdit = navController::navigateToEventEdit,
            navigateToSurveyCheck = navController::navigateToSurveyCheck,
        )
    }
}
