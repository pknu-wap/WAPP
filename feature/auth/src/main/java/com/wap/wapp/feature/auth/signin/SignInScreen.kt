package com.wap.wapp.feature.auth.signin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wap.designsystem.WappTheme
import com.wap.wapp.core.commmon.extensions.toSupportingText
import com.wap.wapp.core.domain.model.AuthState
import com.wap.wapp.core.domain.usecase.auth.SignInUseCase
import com.wap.wapp.feature.auth.R
import kotlinx.coroutines.launch

@Composable
internal fun SignInRoute(
    signInUseCase: SignInUseCase,
    navigateToSignUp: () -> Unit,
    navigateToNotice: () -> Unit,
) {
    SignInScreen(
        signInUseCase = signInUseCase,
        navigateToSignUp = navigateToSignUp,
        navigateToNotice = navigateToNotice,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SignInScreen(
    signInUseCase: SignInUseCase,
    navigateToNotice: () -> Unit,
    navigateToSignUp: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()
    val snackBarHostState = remember { SnackbarHostState() }
    var email by rememberSaveable { mutableStateOf("") }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        snackbarHost = { SnackbarHost(snackBarHostState) },
        sheetContainerColor = WappTheme.colors.backgroundBlack,
        containerColor = WappTheme.colors.backgroundBlack,
        sheetPeekHeight = 0.dp,
        modifier = Modifier.fillMaxSize(),
        sheetContent = {
            Column(
                modifier = Modifier.padding(16.dp),
            ) {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = stringResource(id = R.string.sign_in),
                    style = WappTheme.typography.contentBold,
                    color = WappTheme.colors.white,
                    fontSize = 18.sp,
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(id = R.string.sign_in_email),
                    color = WappTheme.colors.white,
                    style = WappTheme.typography.labelRegular,
                )

                Spacer(modifier = Modifier.height(4.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = email,
                    maxLines = 1,
                    onValueChange = { email = it },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.sign_in_email_hint),
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = WappTheme.colors.white,
                        focusedBorderColor = WappTheme.colors.yellow34,
                    ),
                )
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        coroutineScope.launch {
                            signInUseCase(email = email)
                                .onSuccess {
                                    when (it) {
                                        AuthState.SIGN_IN -> {
                                            navigateToNotice()
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

                Spacer(modifier = Modifier.height(16.dp))

                Divider(
                    color = WappTheme.colors.white,
                    thickness = 1.dp,
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = stringResource(id = R.string.sign_in_find_email),
                    style = WappTheme.typography.captionMedium,
                    color = WappTheme.colors.yellow34,
                )

                Spacer(modifier = Modifier.height(32.dp))
            }
        },
    ) {
        SignInContent(
            openSignInSheet = {
                coroutineScope.launch {
                    scaffoldState.bottomSheetState.expand()
                }
            },
            navigateToNotice = { navigateToNotice() },
        )
    }
}
