package com.wap.wapp.feature.splash

import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.wap.wapp.core.commmon.extensions.TrackScreenViewEvent
import com.wap.wapp.core.commmon.extensions.toSupportingText
import com.wap.wapp.core.designresource.R.drawable
import com.wap.wapp.feature.splash.R.string

@Composable
internal fun SplashRoute(
    viewModel: SplashViewModel = hiltViewModel(),
    navigateToAuth: () -> Unit,
    navigateToNotice: () -> Unit,
) {
    val context = LocalContext.current
    val isIconLogoVisible by viewModel.isIconLogoVisible.collectAsStateWithLifecycle()
    val isIconLogoGoUp by viewModel.isIconLogoGoUp.collectAsStateWithLifecycle()

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

    SplashScreen(
        isIconLogoVisible = isIconLogoVisible,
        isIconLogoGoUp = isIconLogoGoUp,
    )
}

@Composable
internal fun SplashScreen(
    isIconLogoVisible: Boolean,
    isIconLogoGoUp: Boolean,
) {
    val ANIMATION_MILLS = 400

    TrackScreenViewEvent(screenName = "SplashScreen")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WappTheme.colors.backgroundBlack),
    ) {
        AnimatedContent(
            targetState = isIconLogoVisible,
            transitionSpec = {
                scaleIn(tween(ANIMATION_MILLS, ANIMATION_MILLS)) togetherWith
                    scaleOut(tween(ANIMATION_MILLS))
            },
        ) { isIconLogoVisible ->
            if (!isIconLogoVisible) {
                SplashTypoLogo()
            } else {
                SplashIconLogo(isIconLogoGoUp)
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
            painter = painterResource(id = R.drawable.ic_wapp_logo),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(width = 230.dp, height = 230.dp),
            contentDescription = stringResource(id = string.wapp_icon_description),
        )
    }
}

@Composable
private fun SplashIconLogo(isIconLogoGoUp: Boolean) {
    val animatedPadding by animateDpAsState(
        targetValue = if (isIconLogoGoUp) 200.dp else 0.dp,
        animationSpec = tween(1000),
    )

    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .padding(bottom = animatedPadding),
        ) {
            Image(
                painter = painterResource(id = drawable.img_white_cat),
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
}
