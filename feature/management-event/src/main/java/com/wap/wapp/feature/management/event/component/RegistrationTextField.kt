package com.wap.wapp.feature.management.event.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.wap.designsystem.WappTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun RegistrationTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    textAlign: TextAlign? = null,
    align: Alignment = Alignment.TopStart,
    placeholder: String,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = WappTheme.typography.contentMedium.copy(
            textAlign = textAlign,
            color = WappTheme.colors.white,
        ),
        cursorBrush = SolidColor(WappTheme.colors.yellow34),
        visualTransformation = VisualTransformation.None,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
        singleLine = true,
        modifier = modifier.background(
            color = WappTheme.colors.black25,
            shape = RoundedCornerShape(10.dp),
        ),
    ) { innerTextField ->
        Box(modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp)) {
            if (value.isEmpty()) {
                Text(
                    text = placeholder,
                    color = WappTheme.colors.gray82,
                    textAlign = TextAlign.Center,
                    style = WappTheme.typography.contentMedium,
                    modifier = Modifier.align(align),
                )
            }

            Box(modifier = Modifier.align(align)) {
                innerTextField()
            }
        }
    }
}
