package com.wap.wapp.navigation

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.wap.designsystem.WappTheme
import com.wap.wapp.core.domain.usecase.auth.SignInUseCase
import com.wap.wapp.feature.attendance.management.navigation.attendanceManagementScreen
import com.wap.wapp.feature.attendance.management.navigation.navigateToAttendanceManagement
import com.wap.wapp.feature.attendance.navigation.attendanceNavigationRoute
import com.wap.wapp.feature.attendance.navigation.attendanceScreen
import com.wap.wapp.feature.attendance.navigation.navigateToAttendance
import com.wap.wapp.feature.auth.signin.navigation.navigateToSignIn
import com.wap.wapp.feature.auth.signin.navigation.signInNavigationRoute
import com.wap.wapp.feature.auth.signin.navigation.signInScreen
import com.wap.wapp.feature.auth.signup.navigation.navigateToSignUp
import com.wap.wapp.feature.auth.signup.navigation.signUpScreen
import com.wap.wapp.feature.management.event.navigation.managementEventNavGraph
import com.wap.wapp.feature.management.event.navigation.navigateToEventEdit
import com.wap.wapp.feature.management.event.navigation.navigateToEventRegistration
import com.wap.wapp.feature.management.navigation.managementScreen
import com.wap.wapp.feature.management.navigation.navigateToManagement
import com.wap.wapp.feature.management.survey.navigation.managementSurveyNavGraph
import com.wap.wapp.feature.management.survey.navigation.navigateToSurveyFormEdit
import com.wap.wapp.feature.management.survey.navigation.navigateToSurveyFormRegistration
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
import com.wap.wapp.feature.survey.check.navigation.SurveyCheckRoute.surveyCheckRoute
import com.wap.wapp.feature.survey.check.navigation.SurveyDetailBackStack
import com.wap.wapp.feature.survey.check.navigation.navigateToSurveyCheck
import com.wap.wapp.feature.survey.check.navigation.navigateToSurveyDetail
import com.wap.wapp.feature.survey.check.navigation.surveyCheckNavGraph
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
            navigateToNotice = {
                navController.navigateToNotice(
                    navOptions { popUpTo(splashNavigationRoute) { inclusive = true } },
                )
            },
        )
        signInScreen(
            signInUseCase = signInUseCase,
            navigateToNotice = {
                navController.navigateToNotice(
                    navOptions { popUpTo(signInNavigationRoute) { inclusive = true } },
                )
            },
            navigateToSignUp = navController::navigateToSignUp,
        )
        signUpScreen(
            navigateToNotice = {
                navController.navigateToNotice(
                    navOptions { popUpTo(navController.graph.id) { inclusive = true } },
                )
            },
            navigateToSignIn = navController::navigateToSignIn,
        )
        noticeScreen()
        surveyNavGraph(
            navigateToSurvey = navController::navigateToSurvey,
            navigateToSurveyAnswer = navController::navigateToSurveyAnswer,
            navigateToSignIn = navController::navigateToSignIn,
            navigateToSurveyCheck = navController::navigateToSurveyCheck,
        )
        surveyCheckNavGraph(
            navigateToSurveyCheck = {
                navController.navigateToSurveyCheck(
                    navOptions { popUpTo(surveyCheckRoute) { inclusive = true } },
                )
            },
            navigateToSurveyDetail = navController::navigateToSurveyDetail,
            navigateToSurvey = {
                navController.navigateToSurvey(
                    navOptions { popUpTo(surveyCheckRoute) { inclusive = true } },
                )
            },
            navigateToProfile = navController::navigateToProfile,
        )
        managementSurveyNavGraph(
            navigateToManagement = navController::navigateToManagement,
        )
        managementEventNavGraph(
            navigateToManagement = navController::navigateToManagement,
        )
        profileScreen(
            navigateToProfileSetting = navController::navigateToProfileSetting,
            navigateToAttendance = navController::navigateToAttendance,
            navigateToSignIn = {
                navController.navigateToSignIn(navOptions { popUpTo(profileNavigationRoute) })
            },
            navigateToSurveyDetail = { surveyId ->
                navController.navigateToSurveyDetail(
                    surveyId = surveyId,
                    backStack = SurveyDetailBackStack.PROFILE,
                    navOptions = navOptions { popUpTo(profileNavigationRoute) },
                )
            },
        )
        attendanceScreen(
            navigateToSignIn = {
                navController.navigateToSignIn(navOptions { popUpTo(attendanceNavigationRoute) })
            },
            navigateToAttendanceManagement = navController::navigateToAttendanceManagement,
        )
        attendanceManagementScreen(navigateToAttendance = navController::navigateToAttendance)
        profileSettingScreen(
            navigateToSignIn = {
                navController.navigateToSignIn(
                    navOptions {
                        popUpTo(signInNavigationRoute) { inclusive = true }
                    },
                )
            },
            navigateToProfile = {
                navController.navigateToProfile(
                    navOptions {
                        popUpTo(profileSettingNavigationRoute) { inclusive = true }
                    },
                )
            },
        )
        managementScreen(
            navigateToSurveyRegistration = navController::navigateToSurveyFormRegistration,
            navigateToEventRegistration = navController::navigateToEventRegistration,
            navigateToEventEdit = navController::navigateToEventEdit,
            navigateToSurveyFormEdit = navController::navigateToSurveyFormEdit,
            navigateToSignIn = navController::navigateToSignIn,
        )
    }
}
