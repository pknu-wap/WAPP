package com.wap.wapp.feature.profile.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.wap.designsystem.WappTheme
import com.wap.wapp.core.designresource.R

@Composable
internal fun WappSurveyHistoryRow(
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(horizontal = 10.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f),
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_yellow_check),
                contentDescription = "",
            )

            Text(
                text = "프로젝트 세미나에서 보완해야 할 점이 많아요 아싸라비야",
                style = WappTheme.typography.labelRegular,
                color = WappTheme.colors.white,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(start = 10.dp),
            )
        }
        Image(
            painter = painterResource(id = R.drawable.ic_small_right_arrow),
            contentDescription = "",
            modifier = Modifier.padding(start = 10.dp),
        )
    }
}
