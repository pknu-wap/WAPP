package com.wap.wapp.feature.splash

import android.app.Activity
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wap.designsystem.WappTheme
import com.wap.wapp.core.commmon.extensions.showToast
import com.wap.wapp.core.commmon.extensions.toSupportingText
import com.wap.wapp.core.designresource.R
import com.wap.wapp.feature.splash.R.string

@Composable
internal fun SplashRoute(
    viewModel: SplashViewModel = hiltViewModel(),
    navigateToAuth: () -> Unit,
    navigateToNotice: () -> Unit,
) {
    val context = LocalContext.current as Activity
    val isLogoVisible by viewModel.isLogoVisible.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.splashUiEvent.collect { event ->
            when (event) {
                is SplashViewModel.SplashEvent.SignInUser -> navigateToNotice()
                is SplashViewModel.SplashEvent.NonSignInUser -> navigateToAuth()
                is SplashViewModel.SplashEvent.Failure -> {
                    navigateToAuth()
                    context.showToast(event.throwable.toSupportingText())
                }
            }
        }
    }
    SplashScreen(isLogoVisible)
}

@Composable
internal fun SplashScreen(isLogoVisible: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WappTheme.colors.backgroundBlack),
    ) {
        AnimatedContent(targetState = isLogoVisible) { isLogoVisible ->
            if (!isLogoVisible) {
                SplashTypoLogo()
            } else {
                SplashIconLogo()
            }
        }
    }
}

@Composable
private fun SplashTypoLogo() {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Image(
            painter = painterResource(id = com.wap.wapp.feature.splash.R.drawable.ic_wapp_logo),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(width = 230.dp, height = 230.dp),
            contentDescription = stringResource(id = string.wapp_icon_description),
        )
    }
}

@Composable
private fun SplashIconLogo() {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_white_cat),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(width = 230.dp, height = 230.dp),
            contentDescription = stringResource(id = string.wapp_icon_description),
        )

        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally),
        ) {
            Column {
                Spacer(modifier = Modifier.height(40.dp))

                Text(
                    text = stringResource(id = string.application_name),
                    style = WappTheme.typography.titleBold,
                    fontSize = 48.sp,
                    color = WappTheme.colors.white,
                )
            }
            Text(
                text = stringResource(id = string.application_name),
                fontSize = 48.sp,
                style = WappTheme.typography.titleBold,
                color = WappTheme.colors.yellow34,
            )
        }
    }
}
