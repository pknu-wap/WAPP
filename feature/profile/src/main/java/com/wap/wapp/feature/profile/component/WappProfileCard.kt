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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wap.designsystem.WappTheme

@Composable
internal fun WappProfileCard(
    position: String,
    githubImage: Int,
    catImage: Int,
    brush: Brush,
    userName: String,
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(start = 10.dp, end = 10.dp),
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
                        text = userName,
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
