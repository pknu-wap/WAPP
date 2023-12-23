package com.wap.wapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.wap.designsystem.WappTheme
import com.wap.wapp.component.WappBottomBar
import com.wap.wapp.core.domain.usecase.auth.SignInUseCase
import com.wap.wapp.feature.auth.signin.navigation.signInNavigationRoute
import com.wap.wapp.feature.auth.signup.navigation.signUpNavigationRoute
import com.wap.wapp.feature.management.registration.event.navigation.eventRegistrationNavigationRoute
import com.wap.wapp.feature.management.registration.survey.navigation.surveyRegistrationNavigationRoute
import com.wap.wapp.feature.splash.navigation.splashNavigationRoute
import com.wap.wapp.navigation.TopLevelDestination
import com.wap.wapp.navigation.WappNavHost
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var signInUseCase: SignInUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WappTheme {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(WappTheme.colors.backgroundBlack),
                    bottomBar = {
                        val navBackStackEntry by
                        navController.currentBackStackEntryAsState()

                        val currentRoute = navBackStackEntry?.destination?.route
                        var bottomBarState by rememberSaveable { mutableStateOf(false) }

                        handleBottomBarState(
                            currentRoute,
                            setBottomBarState = { boolean ->
                                bottomBarState = boolean
                            },
                        )

                        WappBottomBar(
                            currentRoute = currentRoute,
                            bottomBarState = bottomBarState,
                            onNavigateToDestination = { destination ->
                                navigateToTopLevelDestination(
                                    navController,
                                    destination,
                                )
                            },
                            modifier = Modifier.height(70.dp),
                        )
                    },
                ) { innerPadding ->
                    WappNavHost(
                        signInUseCase = signInUseCase,
                        navController = navController,
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}

private fun handleBottomBarState(
    currentRoute: String?,
    setBottomBarState: (Boolean) -> Unit,
): Unit = when (currentRoute) {
    null -> setBottomBarState(false)
    signInNavigationRoute -> setBottomBarState(false)
    signUpNavigationRoute -> setBottomBarState(false)
    splashNavigationRoute -> setBottomBarState(false)
    surveyRegistrationNavigationRoute -> setBottomBarState(false)
    eventRegistrationNavigationRoute -> setBottomBarState(false)
    else -> setBottomBarState(true)
}

private fun navigateToTopLevelDestination(
    navController: NavController,
    destination: TopLevelDestination,
) {
    navController.navigate(route = destination.route) {
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}
