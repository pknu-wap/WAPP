package com.wap.wapp.feature.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wap.designsystem.WappTheme
import com.wap.wapp.core.designresource.R.drawable
import com.wap.wapp.core.designresource.R.string
import com.wap.wapp.feature.profile.component.WappProfileCard
import com.wap.wapp.feature.profile.screen.GuestProfile
import com.wap.wapp.feature.profile.screen.UserProfile

@Composable
internal fun ProfileRoute(
    viewModel: ProfileViewModel = hiltViewModel(),
    navigateToProfileSetting: () -> Unit,
    navigateToSignInScreen: () -> Unit,
) {
    val eventsState by viewModel.todayEvents.collectAsStateWithLifecycle()

    ProfileScreen(
        eventsState = eventsState,
        navigateToProfileSetting = navigateToProfileSetting,
        navigateToSignInScreen = navigateToSignInScreen,
    )
}

@Composable
internal fun ProfileScreen(
    role: Role = Role.MANAGER,
    userName: String = "",
    eventsState: ProfileViewModel.EventsState,
    navigateToProfileSetting: () -> Unit,
    navigateToSignInScreen: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WappTheme.colors.backgroundBlack),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 25.dp),
        ) {
            Text(
                text = stringResource(id = string.profile),
                style = WappTheme.typography.titleBold.copy(fontSize = 20.sp),
                color = WappTheme.colors.white,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 25.dp),
            )

            if (role == Role.GUEST) {
                return@Box
            }

            Image(
                painter =
                painterResource(id = drawable.ic_subtract),
                contentDescription = stringResource(id = R.string.profile_setting_description),
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 20.dp)
                    .clickable { navigateToProfileSetting() },
            )
        }

        when (role) {
            Role.MANAGER -> {
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
                    userName = "$userName 님",
                )

                UserProfile(eventsState = eventsState)
            }

            Role.NORMAL -> {
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
                    userName = "$userName 님",
                )

                UserProfile(eventsState = eventsState)
            }

            Role.GUEST -> {
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

                GuestProfile(navigateToSignInScreen = navigateToSignInScreen)
            }
        }
    }
}
