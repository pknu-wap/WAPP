package com.wap.wapp.feature.notice

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.wap.designsystem.WappTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Calendar(
    coroutineScope: CoroutineScope,
    bottomSheetState: SheetState,
    dateAndDayOfWeek: Pair<String, String>,
    measureDefaultModifier: Modifier,
    measureExpandableModifier: Modifier,
) {
    Column(
        modifier = measureDefaultModifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = measureExpandableModifier,
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_threelines),
                contentDescription =
                stringResource(R.string.calendarToggleImageContextDescription),
                modifier = Modifier
                    .clickable {
                        toggleBottomSheetState(
                            coroutineScope,
                            bottomSheetState,
                        )
                    }
                    .padding(start = 16.dp),
            )
            Text(
                text = dateAndDayOfWeek.first.substring(
                    DateUtil.YEAR_MONTH_START_INDEX,
                    DateUtil.YEAR_MONTH_END_INDEX,
                ),
                style = WappTheme.typography.titleBold,
                color = WappTheme.colors.white,
                modifier = Modifier.padding(start = 10.dp),
            )
        }

        Image(
            painter = painterResource(id = R.drawable.ic_calendar),
            contentDescription = stringResource(id = R.string.calendarContextDescription),
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth(),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
private fun toggleBottomSheetState(
    coroutineScope: CoroutineScope,
    sheetState: SheetState,
) = coroutineScope.launch {
    when (sheetState.currentValue) {
        SheetValue.Expanded -> sheetState.partialExpand()
        SheetValue.PartiallyExpanded -> sheetState.expand()
        SheetValue.Hidden -> sheetState.expand()
    }
}
