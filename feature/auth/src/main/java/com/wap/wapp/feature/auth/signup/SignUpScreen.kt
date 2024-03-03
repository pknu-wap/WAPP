package com.wap.wapp.feature.auth.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wap.designsystem.WappTheme
import com.wap.designsystem.component.WappSubTopBar
import com.wap.designsystem.modifier.addFocusCleaner
import com.wap.wapp.core.commmon.extensions.toSupportingText
import com.wap.wapp.core.designresource.R
import com.wap.wapp.feature.auth.R.drawable.ic_card
import com.wap.wapp.feature.auth.R.drawable.ic_door
import com.wap.wapp.feature.auth.R.string
import com.wap.wapp.feature.auth.signup.SignUpViewModel.SignUpEvent
import com.wap.wapp.feature.auth.signup.validation.CodeValidationDialog
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun SignUpRoute(
    viewModel: SignUpViewModel = hiltViewModel(),
    navigateToSignIn: () -> Unit,
    navigateToNotice: () -> Unit,
) {
    SignUpScreen(
        viewModel = viewModel,
        navigateToNotice = navigateToNotice,
        navigateToSignIn = navigateToSignIn,
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun SignUpScreen(
    viewModel: SignUpViewModel,
    navigateToNotice: () -> Unit,
    navigateToSignIn: () -> Unit,
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    var showCodeValidationDialog by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        viewModel.signUpEventFlow.collectLatest {
            when (it) {
                is SignUpEvent.SignUpSuccess -> navigateToNotice()

                is SignUpEvent.ValidationSuccess -> {
                    showCodeValidationDialog = true
                }

                is SignUpEvent.Failure ->
                    snackBarHostState.showSnackbar(message = it.throwable.toSupportingText())
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0.dp),
        containerColor = WappTheme.colors.backgroundBlack,
        snackbarHost = { SnackbarHost(snackBarHostState) },
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .addFocusCleaner(focusManager)
                .padding(paddingValue),
        ) {
            if (showCodeValidationDialog) {
                CodeValidationDialog(
                    code = viewModel.wapMemberCode.collectAsStateWithLifecycle().value,
                    setValidationCode = viewModel::setWapMemberCode,
                    onConfirmRequest = viewModel::postUserProfile,
                    onDismissRequest = { showCodeValidationDialog = false },
                    isError = viewModel.isError.collectAsStateWithLifecycle().value,
                    supportingText =
                    stringResource(
                        viewModel.errorSupportingText.collectAsStateWithLifecycle().value,
                    ),
                )
            }

            WappSubTopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                titleRes = string.sign_up,
                showLeftButton = true,
                onClickLeftButton = { navigateToSignIn() },
            )

            Column(modifier = Modifier.padding(top = 40.dp, start = 20.dp, end = 20.dp)) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(id = string.sign_up_title),
                    style = WappTheme.typography.titleBold,
                    fontSize = 22.sp,
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
                    modifier = Modifier.padding(bottom = 20.dp),
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
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done,
                        ),
                        keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                    )

                    SignUpTextField(
                        iconDescription =
                        stringResource(id = string.sign_up_student_id_description),
                        text = viewModel.signUpStudentId.collectAsState().value,
                        title = stringResource(id = string.sign_up_student_id_title),
                        onValueChanged = { name -> viewModel.setStudentId(name) },
                        hint = stringResource(id = string.sign_up_student_id_hint),
                        supportingText = stringResource(id = string.sign_up_student_id_caption),
                        icon = ic_card,
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done,
                        ),
                        keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
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
                            supportingText =
                            stringResource(id = string.sign_up_registered_at_caption),
                            icon = ic_door,
                            modifier = Modifier.width(150.dp),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done,
                            ),
                            keyboardActions =
                            KeyboardActions(onDone = { keyboardController?.hide() }),
                        )

                        SignUpChip(
                            selectedItem = viewModel.signUpSemester.collectAsState().value,
                            onSelected = { semester -> viewModel.setSemester(semester) },
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        onClick = { viewModel.validateUserInformation() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = WappTheme.colors.yellow34,
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
