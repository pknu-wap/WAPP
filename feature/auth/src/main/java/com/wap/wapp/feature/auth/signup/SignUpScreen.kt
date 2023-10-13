package com.wap.wapp.feature.auth.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wap.designsystem.WappTheme
import com.wap.wapp.core.designresource.R
import com.wap.wapp.feature.auth.R.drawable.ic_card
import com.wap.wapp.feature.auth.R.drawable.ic_door
import com.wap.wapp.feature.auth.R.string

@Composable
fun SignUpScreen() {
    WappTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = WappTheme.colors.backgroundBlack,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Image(
                        painterResource(id = R.drawable.ic_back_btn),
                        contentDescription = stringResource(id = string.back_button_description),
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .size(20.dp),
                    )
                    Text(
                        text = stringResource(id = string.sign_up),
                        style = WappTheme.typography.titleBold,
                        color = WappTheme.colors.white,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        textAlign = TextAlign.Center,
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = stringResource(id = string.sign_up_title),
                    style = WappTheme.typography.titleBold,
                    color = WappTheme.colors.white,
                )
                Text(
                    text = stringResource(id = string.sign_up_content),
                    style = WappTheme.typography.contentMedium,
                    color = WappTheme.colors.gray1,
                )

                Spacer(modifier = Modifier.height(32.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(32.dp),
                ) {
                    SignUpTextField(
                        iconDescription = stringResource(id = string.sign_up_name_description),
                        fieldName = stringResource(id = string.sign_up_name_title),
                        fieldHint = stringResource(id = string.sign_up_name_hint),
                        fieldSupportingText = stringResource(id = string.sign_up_name_caption),
                        fieldIcon = R.drawable.ic_profile,
                    )

                    SignUpTextField(
                        iconDescription = stringResource(
                            id = string.sign_up_student_id_description,
                        ),
                        fieldName = stringResource(id = string.sign_up_student_id_title),
                        fieldHint = stringResource(id = string.sign_up_student_id_hint),
                        fieldSupportingText = stringResource(
                            id = string.sign_up_student_id_caption,
                        ),
                        fieldIcon = ic_card,
                    )

                    var dummyText = ""
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                painter = painterResource(id = ic_door),
                                contentDescription = stringResource(
                                    id = string.sign_up_registered_at_description,
                                ),
                                tint = WappTheme.colors.white,
                                modifier = Modifier.size(20.dp),
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = stringResource(id = string.sign_up_registered_at_title),
                                color = WappTheme.colors.white,
                                style = WappTheme.typography.contentBold,
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            TextField(
                                value = dummyText,
                                onValueChange = { dummyText = it },
                                modifier = Modifier.width(150.dp),
                                colors = TextFieldDefaults.textFieldColors(
                                    textColor = WappTheme.colors.white,
                                    backgroundColor = WappTheme.colors.backgroundBlack,
                                    focusedIndicatorColor = WappTheme.colors.white,
                                    unfocusedIndicatorColor = WappTheme.colors.white,
                                ),
                                placeholder = {
                                    Text(
                                        text = stringResource(
                                            id = string.sign_up_registered_at_hint,
                                        ),
                                        color = WappTheme.colors.gray1,
                                    )
                                },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number,
                                ),
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            SignUpChipGroup()
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = stringResource(id = string.sign_up_registered_at_caption),
                            color = WappTheme.colors.yellow,
                            style = WappTheme.typography.captionRegular,
                        )
                    }

                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = WappTheme.colors.yellow,
                        ),
                        shape = RoundedCornerShape(10.dp),
                    ) {
                        Text(
                            text = stringResource(id = string.done),
                            style = WappTheme.typography.contentMedium,
                            color = WappTheme.colors.white,
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun previewSignUpScreen() {
    SignUpScreen()
}
