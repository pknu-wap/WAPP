package com.wap.wapp.feature.attendance.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.wap.designsystem.WappTheme
import com.wap.wapp.feature.attendance.R

@Composable
internal fun AttendanceCheckButton(
    onAttendanceCheckButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ElevatedButton(
        modifier = modifier.height(48.dp),
        onClick = { onAttendanceCheckButtonClicked() },
        colors = ButtonDefaults.buttonColors(
            contentColor = WappTheme.colors.black,
            containerColor = WappTheme.colors.yellow34,
            disabledContentColor = WappTheme.colors.white,
            disabledContainerColor = WappTheme.colors.grayA2,
        ),
        shape = RoundedCornerShape(8.dp),
        content = {
            Row {
                Text(
                    text = stringResource(R.string.go_to_management_attendance_code),
                    style = WappTheme.typography.contentRegular,
                )
            }
        },
    )
}
