package com.wap.wapp.feature.attendance.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.wap.designsystem.WappTheme
import com.wap.wapp.core.model.event.Event
import com.wap.wapp.feature.attendance.R

@Composable
internal fun AttendanceDialog(
    attendanceCode: String,
    event: Event,
    onConfirmRequest: () -> Unit,
    onDismissRequest: () -> Unit,
    onAttendanceCodeChanged: (String) -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
        ),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(WappTheme.colors.black25),
        ) {
            Text(
                text = stringResource(R.string.attendance),
                style = WappTheme.typography.contentBold.copy(fontSize = 20.sp),
                color = WappTheme.colors.yellow34,
                modifier = Modifier.padding(top = 16.dp),
            )

            Divider(
                color = WappTheme.colors.gray82,
                modifier = Modifier.padding(horizontal = 12.dp),
            )

            Text(
                text = generateDialogContentString(event.title),
                style = WappTheme.typography.contentRegular,
                color = WappTheme.colors.white,
            )

            OutlinedTextField(
                value = attendanceCode,
                onValueChange = onAttendanceCodeChanged,
                textStyle = WappTheme.typography.titleRegular.copy(textAlign = TextAlign.Center),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = WappTheme.colors.white,
                    focusedBorderColor = WappTheme.colors.yellow34,
                    unfocusedBorderColor = WappTheme.colors.yellow34,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 16.dp),
            ) {
                Button(
                    onClick = {
                        onConfirmRequest()
                        onDismissRequest()
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WappTheme.colors.yellow34,
                    ),
                    contentPadding = PaddingValues(vertical = 12.dp),
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        text = stringResource(id = R.string.complete),
                        style = WappTheme.typography.titleRegular,
                        color = WappTheme.colors.black,
                    )
                }

                Button(
                    onClick = onDismissRequest,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WappTheme.colors.black25,
                    ),
                    contentPadding = PaddingValues(vertical = 12.dp),
                    modifier = Modifier
                        .weight(1f)
                        .border(
                            width = 1.dp,
                            color = WappTheme.colors.yellow34,
                            shape = RoundedCornerShape(8.dp),
                        ),
                ) {
                    Text(
                        text = stringResource(R.string.cancel),
                        style = WappTheme.typography.titleRegular,
                        color = WappTheme.colors.yellow34,
                    )
                }
            }
        }
    }
}

@Composable
private fun generateDialogContentString(eventTitle: String) = buildAnnotatedString {
    withStyle(
        style = SpanStyle(
            textDecoration = TextDecoration.Underline,
            color = WappTheme.colors.yellow34,
        ),
    ) {
        append(eventTitle)
    }
    append(" 일정에 출석합니다.")
}
