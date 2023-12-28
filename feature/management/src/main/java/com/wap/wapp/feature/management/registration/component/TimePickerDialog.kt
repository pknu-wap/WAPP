package com.wap.wapp.feature.management.registration.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.wap.designsystem.WappTheme
import com.wap.wapp.feature.management.R
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TimePickerDialog(
    state: TimePickerState,
    onDismissRequest: () -> Unit,
    onConfirmButtonClicked: (LocalTime) -> Unit,
    onDismissButtonClicked: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Card {
            Column(modifier = Modifier.background(WappTheme.colors.black25)) {
                TimePicker(
                    state = state,
                    colors = TimePickerDefaults.colors(
                        selectorColor = WappTheme.colors.yellow34,
                        clockDialSelectedContentColor = WappTheme.colors.black25,
                        clockDialUnselectedContentColor = WappTheme.colors.gray95,
                        clockDialColor = WappTheme.colors.black25,
                        periodSelectorBorderColor = Color.Transparent,
                        timeSelectorSelectedContainerColor = WappTheme.colors.yellow34,
                        timeSelectorUnselectedContainerColor = WappTheme.colors.black25,
                        timeSelectorSelectedContentColor = WappTheme.colors.black25,
                        timeSelectorUnselectedContentColor = WappTheme.colors.gray95,
                        periodSelectorSelectedContentColor = WappTheme.colors.black25,
                        periodSelectorUnselectedContentColor = WappTheme.colors.gray95,
                        periodSelectorSelectedContainerColor = WappTheme.colors.yellow34,
                        periodSelectorUnselectedContainerColor = WappTheme.colors.black25,
                    ),
                    modifier = Modifier.padding(16.dp),
                )

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                ) {
                    Text(
                        text = stringResource(R.string.cancel),
                        color = WappTheme.colors.grayBD,
                        style = WappTheme.typography.contentBold,
                        modifier = Modifier
                            .padding(end = 30.dp)
                            .clickable { onDismissButtonClicked() },
                    )

                    Text(
                        stringResource(R.string.select),
                        color = WappTheme.colors.grayBD,
                        style = WappTheme.typography.contentBold,
                        modifier = Modifier
                            .padding(end = 20.dp)
                            .clickable {
                                onConfirmButtonClicked(
                                    LocalTime.of(state.hour, state.minute),
                                )
                            },
                    )
                }
            }
        }
    }
}
