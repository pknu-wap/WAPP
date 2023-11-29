package com.wap.wapp.feature.profile.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wap.designsystem.WappTheme
import com.wap.wapp.core.designresource.R.drawable
import com.wap.wapp.feature.profile.R

@Composable
internal fun WappProfileCard(
    isManager: Boolean,
    userName: String,
) {
    var position = stringResource(R.string.normal)
    var githubImage = drawable.ic_normal_github
    var catImage = drawable.ic_normal_cat
    var brush = Brush.horizontalGradient(
        listOf(
            WappTheme.colors.yellow3C,
            WappTheme.colors.yellow34,
            WappTheme.colors.yellowA4,
        ),
    )

    if (isManager) {
        position = stringResource(R.string.manager)
        githubImage = drawable.ic_manager_github
        catImage = drawable.ic_manager_cat
        brush = Brush.horizontalGradient(
            listOf(
                WappTheme.colors.blue2FF,
                WappTheme.colors.blue4FF,
                WappTheme.colors.blue1FF,
            ),
        )
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(top = 30.dp, start = 10.dp, end = 10.dp),
    ) {
        Box(
            modifier = Modifier.background(
                brush = brush,
            ),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.align(Alignment.CenterStart),
            ) {
                Image(
                    painter = painterResource(id = githubImage),
                    contentDescription = "",
                    modifier = Modifier.padding(start = 20.dp),
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                    modifier = Modifier.padding(start = 5.dp),
                ) {
                    Text(
                        text = "$userName 님",
                        style = WappTheme.typography.contentRegular.copy(fontSize = 20.sp),
                        color = WappTheme.colors.white,
                    )

                    Text(
                        text = position,
                        style = WappTheme.typography.labelRegular,
                        color = WappTheme.colors.white,
                    )
                }
            }

            Image(
                painter = painterResource(id = catImage),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 10.dp, top = 25.dp),
            )
        }
    }
}

@Preview
@Composable
private fun NormalProfileCard() {
    WappProfileCard(isManager = false, userName = "WAPP")
}

@Preview
@Composable
private fun ManagerProfileCard() {
    WappProfileCard(isManager = true, userName = "WAPP")
}
