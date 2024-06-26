package com.wap.wapp.feature.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wap.designsystem.WappTheme
import com.wap.designsystem.component.CircleLoader
import com.wap.designsystem.component.WappLeftMainTopBar
import com.wap.wapp.core.commmon.extensions.TrackScreenViewEvent
import com.wap.wapp.core.commmon.extensions.toSupportingText
import com.wap.wapp.core.designresource.R.drawable
import com.wap.wapp.core.designresource.R.string
import com.wap.wapp.core.model.user.UserProfile
import com.wap.wapp.core.model.user.UserRole
import com.wap.wapp.feature.profile.ProfileViewModel.UserRoleState
import com.wap.wapp.feature.profile.component.WappProfileCard
import com.wap.wapp.feature.profile.profilesetting.component.GuestProfile
import com.wap.wapp.feature.profile.profilesetting.component.UserProfile
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun ProfileRoute(
    viewModel: ProfileViewModel = hiltViewModel(),
    navigateToProfileSetting: (String) -> Unit,
    navigateToAttendance: () -> Unit,
    navigateToSignIn: () -> Unit,
    navigateToSurveyDetail: (String) -> Unit,
) {
    val todayEventsState by viewModel.todayEvents.collectAsStateWithLifecycle()
    val recentEventsState by viewModel.recentEvents.collectAsStateWithLifecycle()
    val userRespondedSurveysState by viewModel.userRespondedSurveys.collectAsStateWithLifecycle()
    val userRoleState by viewModel.userRole.collectAsStateWithLifecycle()
    val userProfile by viewModel.userProfile.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(true) {
        viewModel.errorFlow.collectLatest { throwable ->
            snackBarHostState.showSnackbar(
                message = throwable.toSupportingText(),
            )
        }
    }

    ProfileScreen(
        todayEventsState = todayEventsState,
        recentEventsState = recentEventsState,
        userRoleState = userRoleState,
        userProfile = userProfile,
        userRespondedSurveysState = userRespondedSurveysState,
        snackBarHostState = snackBarHostState,
        navigateToProfileSetting = navigateToProfileSetting,
        navigateToAttendance = navigateToAttendance,
        navigateToSignIn = navigateToSignIn,
        navigateToSurveyDetail = navigateToSurveyDetail,
    )
}

@Composable
internal fun ProfileScreen(
    userRoleState: UserRoleState,
    userProfile: UserProfile,
    todayEventsState: ProfileViewModel.EventsState,
    recentEventsState: ProfileViewModel.EventAttendanceStatusState,
    userRespondedSurveysState: ProfileViewModel.SurveysState,
    snackBarHostState: SnackbarHostState,
    navigateToProfileSetting: (String) -> Unit,
    navigateToAttendance: () -> Unit,
    navigateToSignIn: () -> Unit,
    navigateToSurveyDetail: (String) -> Unit,
) {
    val scrollState = rememberScrollState()

    TrackScreenViewEvent(screenName = "ProfileScreen")

    Scaffold(
        contentWindowInsets = WindowInsets(0.dp),
        snackbarHost = { SnackbarHost(snackBarHostState) },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(WappTheme.colors.backgroundBlack),
        ) {
            when (userRoleState) {
                is UserRoleState.Loading -> {
                    Spacer(modifier = Modifier.weight(1f))
                    CircleLoader(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }

                is UserRoleState.Success -> {
                    WappLeftMainTopBar(
                        titleRes = string.profile,
                        contentRes = R.string.profile_content,
                        settingButtonDescriptionRes = R.string.profile_setting_description,
                        showSettingButton = userRoleState.userRole != UserRole.GUEST,
                        onClickSettingButton = { navigateToProfileSetting(userProfile.userId) },
                    )

                    when (userRoleState.userRole) {
                        UserRole.MANAGER -> {
                            WappProfileCard(
                                position = stringResource(R.string.manager),
                                githubImage = drawable.ic_manager_github,
                                catImage = drawable.ic_manager_cat,
                                brush = Brush.horizontalGradient(
                                    listOf(
                                        WappTheme.colors.blue2FF,
                                        WappTheme.colors.blue4FF,
                                        WappTheme.colors.blue1FF,
                                    ),
                                ),
                                userName = "${userProfile.userName} 님",
                            )

                            UserProfile(
                                todayEventsState = todayEventsState,
                                recentEventsState = recentEventsState,
                                userRespondedSurveysState = userRespondedSurveysState,
                                attendanceCardBoardColor = WappTheme.colors.blue4FF,
                                navigateToAttendance = navigateToAttendance,
                                navigateToSurveyDetail = navigateToSurveyDetail,
                            )
                        }

                        UserRole.MEMBER -> {
                            WappProfileCard(
                                position = stringResource(R.string.normal),
                                githubImage = drawable.ic_normal_github,
                                catImage = drawable.ic_normal_cat,
                                brush = Brush.horizontalGradient(
                                    listOf(
                                        WappTheme.colors.yellow3C,
                                        WappTheme.colors.yellow34,
                                        WappTheme.colors.yellowA4,
                                    ),
                                ),
                                userName = "${userProfile.userName} 님",
                            )

                            UserProfile(
                                todayEventsState = todayEventsState,
                                recentEventsState = recentEventsState,
                                userRespondedSurveysState = userRespondedSurveysState,
                                attendanceCardBoardColor = WappTheme.colors.yellow34,
                                navigateToAttendance = navigateToAttendance,
                                navigateToSurveyDetail = navigateToSurveyDetail,
                            )
                        }

                        UserRole.GUEST -> {
                            WappProfileCard(
                                position = stringResource(R.string.guest),
                                githubImage = drawable.ic_guest_github,
                                catImage = drawable.ic_guest_cat,
                                brush = Brush.horizontalGradient(
                                    listOf(
                                        WappTheme.colors.grayA2,
                                        WappTheme.colors.gray7C,
                                        WappTheme.colors.gray4A,
                                    ),
                                ),
                                userName = stringResource(id = R.string.non_user),
                            )

                            GuestProfile(navigateToSignIn = navigateToSignIn)
                        }
                    }
                }
            }
        }
    }
}
