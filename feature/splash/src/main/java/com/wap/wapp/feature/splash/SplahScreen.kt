package com.wap.wapp.feature.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.wap.designsystem.WappTheme

@Composable
internal fun SplashRoute(
    viewModel: SplashViewModel = hiltViewModel(),
    navigateToAuth: () -> Unit,
) {
    LaunchedEffect(true) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is SplashViewModel.SplashEvent.TimerDone -> navigateToAuth()
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
