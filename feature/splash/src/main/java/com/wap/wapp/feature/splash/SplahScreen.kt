package com.wap.wapp.feature.splash

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.wap.designsystem.WappTheme
import com.wap.wapp.core.commmon.extensions.showToast
import com.wap.wapp.core.commmon.extensions.toSupportingText

@Composable
internal fun SplashRoute(
    viewModel: SplashViewModel = hiltViewModel(),
    navigateToAuth: () -> Unit,
    navigateToNotice: () -> Unit,
) {
    val context = LocalContext.current as Activity

    LaunchedEffect(true) {
        viewModel.splashUiEvent.collect { event ->
            when (event) {
                is SplashViewModel.SplashEvent.SignInUser -> { navigateToNotice() }
                is SplashViewModel.SplashEvent.NonSignInUser -> { navigateToAuth() }
                is SplashViewModel.SplashEvent.Failure -> {
                    navigateToAuth()
                    context.showToast(event.throwable.toSupportingText())
                }
            }
        }
    }

    SplashScreen()
}

@Composable
internal fun SplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(WappTheme.colors.backgroundBlack),
    ) {
        Text(
            text = "splash",
            modifier = Modifier.align(Alignment.Center),
            color = WappTheme.colors.white,
        )
    }
}
