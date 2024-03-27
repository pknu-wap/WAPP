package com.wap.wapp.feature.splash

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wap.designsystem.WappTheme
import com.wap.wapp.core.commmon.extensions.TrackScreenViewEvent
import com.wap.wapp.core.commmon.extensions.toSupportingText
import com.wap.wapp.feature.splash.R.string

@Composable
internal fun SplashRoute(
    viewModel: SplashViewModel = hiltViewModel(),
    navigateToAuth: () -> Unit,
    navigateToNotice: () -> Unit,
) {
    val context = LocalContext.current

    LaunchedEffect(true) {
        viewModel.splashUiEvent.collect { event ->
            when (event) {
                is SplashViewModel.SplashEvent.SignInUser -> navigateToNotice()
                is SplashViewModel.SplashEvent.NonSignInUser -> navigateToAuth()
                is SplashViewModel.SplashEvent.Failure -> {
                    navigateToAuth()
                    Toast.makeText(context, event.throwable.toSupportingText(), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    SplashScreen()
}

@Composable
internal fun SplashScreen() {
    TrackScreenViewEvent(screenName = "SplashScreen")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(WappTheme.colors.backgroundBlack),
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_wapp_logo),
            modifier = Modifier
                .align(Alignment.Center)
                .size(width = 230.dp, height = 230.dp),
            contentDescription = stringResource(id = string.wapp_icon_description),
        )
    }
}
