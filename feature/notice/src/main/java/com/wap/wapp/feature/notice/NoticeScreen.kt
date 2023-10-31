package com.wap.wapp.feature.notice

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wap.designsystem.WappTheme

@Composable
internal fun NoticeScreen(
    viewModel: NoticeViewModel = hiltViewModel(),
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.background(color = WappTheme.colors.black1),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_threelines),
                contentDescription = "공지사항 목록만을 보여줍니다.",
                modifier = Modifier.padding(start = 16.dp),
            )
            Text(
                text = "2023. 10",
                style = WappTheme.typography.titleBold,
                color = WappTheme.colors.white,
                modifier = Modifier.padding(start = 10.dp),
            )
        }
        Image(
            painter = painterResource(id = R.drawable.ic_calendar),
            contentDescription = "공지사항을 띄워주는 달력입니다.",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth(),
        )
    }
}
