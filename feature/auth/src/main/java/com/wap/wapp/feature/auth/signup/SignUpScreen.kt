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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wap.designsystem.WappTheme
import com.wap.wapp.core.designresource.R

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
                        contentDescription = "Back Button",
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .size(20.dp),
                    )
                    Text(
                        text = "회원가입",
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
                    text = "처음 가입하시네요!",
                    style = WappTheme.typography.titleBold,
                    color = WappTheme.colors.white,
                )
                Text(
                    text = "회원님의 정보를 입력해주세요.",
                    style = WappTheme.typography.contentMedium,
                    color = WappTheme.colors.gray1,
                )

                Spacer(modifier = Modifier.height(32.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(32.dp),
                ) {
                    SignUpTextField(
                        fieldName = "이름",
                        fieldHint = "이름을 입력해주세요.",
                        fieldSupportingText = "회원 확인을 위해, 실명을 입력해주세요.",
                        fieldIcon = R.drawable.ic_profile,
                    )

                    SignUpTextField(
                        fieldName = "학번",
                        fieldHint = "학번을 입력해주세요.",
                        fieldSupportingText = "동명이인을 구별하기 위해, 필요해요!",
                        fieldIcon = R.drawable.ic_card,
                    )

                    var dummyText = ""
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_door),
                                contentDescription = "name Icon",
                                tint = WappTheme.colors.white,
                                modifier = Modifier.size(20.dp),
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "입부시기",
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
                                        text = "입부년도 입력",
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
                            text = "회원님의 기수 정보를 알려드릴게요!",
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
                            text = "완료",
                            style = WappTheme.typography.contentMedium,
                            color = WappTheme.colors.white,
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpChipGroup() {
    val itemsList = listOf("1학기", "2학기")

    var selectedItem by remember {
        mutableStateOf(itemsList[0]) // initially, first item is selected
    }

    LazyRow(modifier = Modifier.fillMaxWidth()) {
        items(itemsList) { item ->
            FilterChip(
                modifier = Modifier.padding(horizontal = 6.dp),
                selected = (item == selectedItem),
                onClick = {
                    selectedItem = item
                },
                label = {
                    Text(
                        text = item,
                        color = WappTheme.colors.white,
                        style = WappTheme.typography.contentRegular,
                        textAlign = TextAlign.Center,
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = WappTheme.colors.black3,
                    selectedContainerColor = WappTheme.colors.yellow,
                ),
            )
        }
    }
}

@Preview
@Composable
fun previewSignUpScreen() {
    SignUpScreen()
}
