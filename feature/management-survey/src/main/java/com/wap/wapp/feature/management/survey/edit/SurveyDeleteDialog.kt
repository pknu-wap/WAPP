package com.wap.wapp.feature.management.survey.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.wap.designsystem.WappTheme
import com.wap.wapp.feature.management.survey.R

@Composable
internal fun DeleteSurveyDialog(
    deleteSurvey: () -> Unit,
    onDismissRequest: () -> Unit,
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
                .wrapContentSize()
                .padding(horizontal = 30.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(WappTheme.colors.black25),
        ) {
            Text(
                text = stringResource(id = R.string.delete_survey),
                style = WappTheme.typography.contentBold.copy(fontSize = 20.sp),
                color = WappTheme.colors.yellow34,
                modifier = Modifier.padding(top = 16.dp),
            )

            Divider(
                color = WappTheme.colors.gray82,
                modifier = Modifier.padding(horizontal = 12.dp),
            )

            Text(
                text = generateDialogContentString(),
                style = WappTheme.typography.contentRegular,
                color = WappTheme.colors.white,
                modifier = Modifier.padding(top = 12.dp, start = 12.dp, end = 12.dp),
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 16.dp),
            ) {
                Button(
                    onClick = {
                        deleteSurvey()
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
private fun generateDialogContentString() = buildAnnotatedString {
    append("정말로 해당 설문을 삭제하시겠습니까?\n")
    withStyle(
        style = SpanStyle(
            textDecoration = TextDecoration.Underline,
            color = WappTheme.colors.yellow34,
        ),
    ) {
        append("해당 설문과 관련된 답변들이 모두 삭제됩니다.")
    }
}
