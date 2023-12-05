package com.wap.wapp.feature.splash

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

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
    Column(modifier = Modifier.fillMaxSize()) {
    }
}
