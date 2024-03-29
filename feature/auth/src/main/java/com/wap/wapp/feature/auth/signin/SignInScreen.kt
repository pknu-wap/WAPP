package com.wap.wapp.feature.auth.signin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wap.designsystem.WappTheme
import com.wap.designsystem.modifier.addFocusCleaner
import com.wap.wapp.core.commmon.extensions.TrackScreenViewEvent
import com.wap.wapp.core.commmon.extensions.toSupportingText
import com.wap.wapp.core.domain.model.AuthState
import com.wap.wapp.core.domain.usecase.auth.SignInUseCase
import com.wap.wapp.feature.auth.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
internal fun SignInRoute(
    viewModel: SignInViewModel = hiltViewModel(),
    signInUseCase: SignInUseCase,
    navigateToSignUp: () -> Unit,
    navigateToNotice: () -> Unit,
) {
    SignInScreen(
        signInUseCase = signInUseCase,
        navigateToSignUp = navigateToSignUp,
        onSignInSucceed = {
            viewModel.logUserSignedIn()
            navigateToNotice()
        },
        onGuestModeButtonClicked = navigateToNotice
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
internal fun SignInScreen(
    signInUseCase: SignInUseCase,
    onSignInSucceed: () -> Unit,
    navigateToSignUp: () -> Unit,
    onGuestModeButtonClicked: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()
    val snackBarHostState = remember { SnackbarHostState() }
    var email by rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    TrackScreenViewEvent(screenName = "SignInScreen")

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        snackbarHost = { SnackbarHost(snackBarHostState) },
        sheetContainerColor = WappTheme.colors.backgroundBlack,
        containerColor = WappTheme.colors.backgroundBlack,
        sheetPeekHeight = 0.dp,
        modifier = Modifier.fillMaxSize(),
        sheetContent = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp, bottom = 48.dp)
                    .addFocusCleaner(focusManager),
            ) {
                Text(
                    text = stringResource(id = R.string.sign_in),
                    style = WappTheme.typography.contentBold,
                    color = WappTheme.colors.white,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Text(
                        text = stringResource(id = R.string.sign_in_email),
                        color = WappTheme.colors.white,
                        style = WappTheme.typography.labelRegular,
                    )

                    SignInTextField(
                        email = email,
                        onEmailChanged = { email = it },
                        keyboardController = keyboardController,
                    )
                }

                SignInButton(
                    email = email,
                    coroutineScope = coroutineScope,
                    signInUseCase = signInUseCase,
                    navigateToSignUp = navigateToSignUp,
                    onSignInSucceed = onSignInSucceed,
                    snackBarHostState = snackBarHostState,
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Divider(
                        color = WappTheme.colors.white,
                        thickness = 1.dp,
                    )

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = stringResource(id = R.string.sign_in_find_email),
                        style = WappTheme.typography.captionMedium,
                        color = WappTheme.colors.yellow34,
                    )
                }
            }
        },
    ) {
        SignInContent(
            openSignInSheet = {
                coroutineScope.launch {
                    scaffoldState.bottomSheetState.expand()
                }
            },
            onGuestModeButtonClicked = { onGuestModeButtonClicked() },
            modifier = Modifier.addFocusCleaner(focusManager),
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun SignInTextField(
    email: String,
    onEmailChanged: (String) -> Unit,
    keyboardController: SoftwareKeyboardController?,
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = email,
        maxLines = 1,
        onValueChange = onEmailChanged,
        placeholder = {
            Text(
                text = stringResource(id = R.string.sign_in_email_hint),
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Done,
        ),
        keyboardActions = KeyboardActions(
            onDone = { keyboardController?.hide() },
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = WappTheme.colors.white,
            focusedBorderColor = WappTheme.colors.yellow34,
            unfocusedTextColor = WappTheme.colors.white,
        ),
    )
}

@Composable
private fun SignInButton(
    email: String,
    coroutineScope: CoroutineScope,
    signInUseCase: SignInUseCase,
    onSignInSucceed: () -> Unit,
    navigateToSignUp: () -> Unit,
    snackBarHostState: SnackbarHostState,
) {
    Button(
        onClick = {
            coroutineScope.launch {
                signInUseCase(email = email)
                    .onSuccess {
                        when (it) {
                            AuthState.SIGN_IN -> {
                                onSignInSucceed()
                            }

                            AuthState.SIGN_UP -> {
                                navigateToSignUp()
                            }
                        }
                    }
                    .onFailure { throwable ->
                        snackBarHostState.showSnackbar(
                            throwable.toSupportingText(),
                        )
                    }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        enabled = email.isNotBlank(),
        colors = ButtonDefaults.buttonColors(
            contentColor = WappTheme.colors.white,
            containerColor = WappTheme.colors.yellow34,
            disabledContentColor = WappTheme.colors.white,
            disabledContainerColor = WappTheme.colors.grayA2,
        ),
        shape = RoundedCornerShape(10.dp),
    ) {
        Text(
            text = stringResource(id = R.string.done),
            style = WappTheme.typography.contentMedium,
        )
    }
}
