package com.wap.wapp.feature.auth.signin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wap.designsystem.WappTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun signInBottomSheet() {
    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }

    if (isSheetOpen) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = { isSheetOpen = false },
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
            ) {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "로그인",
                    style = WappTheme.typography.contentMedium,
                    color = WappTheme.colors.white,
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "이메일",
                    color = WappTheme.colors.white,
                    style = WappTheme.typography.labelRegular,
                )
                var filledText = ""
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = filledText,
                    onValueChange = { filledText = it },
                    placeholder = {
                        Text(text = "Github Email")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                    ),
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = WappTheme.colors.white,
                        containerColor = WappTheme.colors.gray1,
                    ),
                    shape = RoundedCornerShape(10.dp),
                ) {
                    Text(
                        text = "완료",
                        style = WappTheme.typography.contentMedium,
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Divider(
                    color = WappTheme.colors.white,
                    thickness = 1.dp,
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "이메일을 까먹었으셨나요?",
                    style = WappTheme.typography.captionMedium,
                    color = WappTheme.colors.yellow,
                )
            }
        }
    }
}

@Preview
@Composable
fun previewSignUpScreen() {
    WappTheme {
        signInBottomSheet()
    }
}
