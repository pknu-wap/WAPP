package com.wap.wapp.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.wap.designsystem.WappTheme
import com.wap.wapp.navigation.TopLevelDestination

@Composable
internal fun WappBottomBar(
    modifier: Modifier = Modifier,
    currentRoute: String?,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    bottomBarState: Boolean,
) {
    AnimatedVisibility(
        visible = bottomBarState,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        content = {
            BottomNavigation(
                backgroundColor = WappTheme.colors.backgroundBlack,
                modifier = modifier
            ) {
                TopLevelDestination.entries.forEach { destination ->
                    val isSelect = currentRoute == destination.route
                    BottomNavigationItem(
                        selected = isSelect,
                        onClick = { onNavigateToDestination(destination) },
                        selectedContentColor = WappTheme.colors.yellow34,
                        unselectedContentColor = WappTheme.colors.grayA2,
                        icon = {
                            Icon(
                                painter = painterResource(id = destination.iconDrawableId),
                                contentDescription = null,
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(id = destination.labelTextId),
                                style = WappTheme.typography.labelNormal,
                                color = if (isSelect) WappTheme.colors.yellow34
                                else WappTheme.colors.grayA2,
                            )
                        }
                    )
                }
            }
        }
    )
}
