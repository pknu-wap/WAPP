package com.wap.wapp.feature.notice

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wap.designsystem.WappTheme
import com.wap.wapp.core.domain.model.Notice
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun NoticeScreen(
    viewModel: NoticeViewModel = hiltViewModel(),
) {
    val localDensity = LocalDensity.current
    var calendarHeight: Dp by remember { mutableStateOf(0.dp) }
    var topBarHeight: Dp by remember { mutableStateOf(0.dp) }
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = SheetState(
            skipPartiallyExpanded = false,
        ),
    )

    val coroutineScope = rememberCoroutineScope()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContainerColor = WappTheme.colors.black1,
        sheetPeekHeight = topBarHeight + calendarHeight,
        sheetContent = {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.height(500.dp),
            ) {
                Text(
                    text = "10.25 수요일",
                    style = WappTheme.typography.titleBold,
                    color = WappTheme.colors.white,
                    modifier = Modifier.padding(start = 15.dp, bottom = 15.dp),
                )

                NoticeList(
                    listOf(
                        Notice(
                            time = "19:00",
                            title = "동아리 MT",
                            duration = "19:00 ~ 21:00",
                        ),
                        Notice(
                            time = "19:00",
                            title = "동아리 MT2",
                            duration = "19:00 ~ 21:00",
                        ),
                        Notice(
                            time = "19:00",
                            title = "동아리 MT3",
                            duration = "19:00 ~ 21:00",
                        ),
                    ),
                )
            }
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WappTheme.colors.black1),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = 10.dp),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_threelines),
                    contentDescription = "공지사항 목록만을 보여줍니다.",
                    modifier = Modifier
                        .clickable {
                            scaffoldState.bottomSheetState.let { sheetState ->
                                handleSheetState(coroutineScope, sheetState)
                            }
                        }
                        .padding(start = 16.dp),
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
}

@OptIn(ExperimentalMaterial3Api::class)
private fun handleSheetState(coroutineScope: CoroutineScope, sheetState: SheetState) {
    coroutineScope.launch {
        when (sheetState.currentValue) {
            SheetValue.Expanded -> sheetState.partialExpand()
            SheetValue.PartiallyExpanded -> sheetState.expand()
            SheetValue.Hidden -> sheetState.expand()
        }
    }
}

@Composable
fun NoticeList(notices: List<Notice>) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 15.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        itemsIndexed(
            items = notices,
            key = { index, notice -> notice.title },
        ) { index, notice ->
            Log.d("test", "$index, $notice 얍얍얍")

            NoticeItem(notice = notice)
        }
    }
}

@Composable
fun NoticeItem(notice: Notice) {
    Column {
        Row(
            modifier = Modifier
                .background(WappTheme.colors.black1)
                .fillMaxWidth()
                .padding(top = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = notice.time,
                style = WappTheme.typography.contentBold,
                color = WappTheme.colors.white,
            )
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .size(width = 4.dp, height = 20.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(WappTheme.colors.yellow),
            )

            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(start = 12.dp),
            ) {
                Text(
                    text = notice.title,
                    style = WappTheme.typography.contentRegular,
                    color = WappTheme.colors.white,
                )

                Text(
                    text = notice.duration,
                    style = WappTheme.typography.captionRegular,
                    color = WappTheme.colors.gray3,
                )
            }
        }
        Divider(
            color = WappTheme.colors.gray4,
            thickness = (0.5).dp,
            modifier = Modifier.padding(top = 15.dp),
        )
    }
}
