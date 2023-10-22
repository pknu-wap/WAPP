package com.wap.wapp.feature.auth.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wap.designsystem.WappTheme
import com.wap.wapp.core.commmon.extensions.toSupportingText
import com.wap.wapp.core.designresource.R
import com.wap.wapp.feature.auth.R.drawable.ic_card
import com.wap.wapp.feature.auth.R.drawable.ic_door
import com.wap.wapp.feature.auth.R.string
import com.wap.wapp.feature.auth.signup.SignUpViewModel.SignUpEvent
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun SignUpScreen(
    navigateToNotice: () -> Unit,
    navigateToSignIn: () -> Unit,
    viewModel: SignUpViewModel = hiltViewModel(),
) {
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(true) {
        viewModel.signUpEventFlow.collectLatest {
            when (it) {
                is SignUpEvent.Success -> {
                    navigateToNotice()
                }
                is SignUpEvent.Failure -> {
                    snackBarHostState.showSnackbar(
                        message = it.throwable.toSupportingText(),
                    )
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = WappTheme.colors.backgroundBlack,
        snackbarHost = { SnackbarHost(snackBarHostState) },
    ) { padding ->
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
                        .size(20.dp)
                        .clickable { navigateToSignIn() },
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
                color = WappTheme.colors.grayA2,
            )

            Spacer(modifier = Modifier.height(32.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(32.dp),
            ) {
                SignUpTextField(
                    iconDescription = stringResource(id = string.sign_up_name_description),
                    text = viewModel.signUpName.collectAsState().value,
                    title = stringResource(id = string.sign_up_name_title),
                    onValueChanged = { name -> viewModel.setName(name) },
                    hint = stringResource(id = string.sign_up_name_hint),
                    supportingText = stringResource(id = string.sign_up_name_caption),
                    icon = R.drawable.ic_profile,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                )

                SignUpTextField(
                    iconDescription = stringResource(
                        id = string.sign_up_student_id_description,
                    ),
                    text = viewModel.signUpStudentId.collectAsState().value,
                    title = stringResource(id = string.sign_up_student_id_title),
                    onValueChanged = { name -> viewModel.setStudentId(name) },
                    hint = stringResource(id = string.sign_up_student_id_hint),
                    supportingText = stringResource(id = string.sign_up_student_id_caption),
                    icon = ic_card,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    SignUpTextField(
                        iconDescription = stringResource(
                            id = string.sign_up_registered_at_description,
                        ),
                        text = viewModel.signUpYear.collectAsState().value,
                        title = stringResource(id = string.sign_up_registered_at_title),
                        onValueChanged = { name -> viewModel.setYear(name) },
                        hint = stringResource(id = string.sign_up_registered_at_hint),
                        supportingText = stringResource(
                            id = string.sign_up_registered_at_caption,
                        ),
                        icon = ic_door,
                        modifier = Modifier.width(150.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    )

                    SignUpChip(
                        selectedItem = viewModel.signUpSemester.collectAsState().value,
                        onSelected = { semester -> viewModel.setSemester(semester) },
                    )
                }

                Button(
                    onClick = { viewModel.postUserProfile() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
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

/*@Preview
@Composable
fun previewSignUpScreen() {
    SignUpScreen(
        "",
        navigateToNotice = { }
    )
}*/
