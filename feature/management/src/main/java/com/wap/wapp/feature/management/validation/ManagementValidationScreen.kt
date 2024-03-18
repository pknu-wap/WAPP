package com.wap.wapp.feature.management.validation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wap.designsystem.WappTheme
import com.wap.designsystem.component.WappButton
import com.wap.designsystem.component.WappTextField
import com.wap.designsystem.modifier.addFocusCleaner
import com.wap.wapp.core.commmon.extensions.toSupportingText
import com.wap.wapp.feature.management.R
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ManagementValidationScreen(
    viewModel: ManagementValidationViewModel = hiltViewModel(),
    onValidationSuccess: () -> Unit,
) {
    val code by viewModel.managementCode.collectAsStateWithLifecycle()
    val isError by viewModel.isError.collectAsStateWithLifecycle()
    val errorSupportingText by viewModel.errorSupportingText.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(true) {
        viewModel.managementCodeUiState.collectLatest {
            when (it) {
                is ManagementValidationViewModel.ManagementCodeUiState.Init -> {}

                is ManagementValidationViewModel.ManagementCodeUiState.Success ->
                    onValidationSuccess()

                is ManagementValidationViewModel.ManagementCodeUiState.Failure ->
                    snackBarHostState.showSnackbar(it.throwable.toSupportingText())
            }
        }
    }

    Scaffold(
        containerColor = WappTheme.colors.backgroundBlack,
        snackbarHost = { SnackbarHost(snackBarHostState) },
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .addFocusCleaner(focusManager),
        ) {
            Text(
                text = stringResource(R.string.management_dialog_title),
                style = WappTheme.typography.titleBold,
                color = WappTheme.colors.white,
                textAlign = TextAlign.Center,
            )

            Text(
                text = stringResource(R.string.management_dialog_content),
                style = WappTheme.typography.captionMedium,
                color = WappTheme.colors.white,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.padding(vertical = 8.dp))

            WappTextField(
                value = code,
                onValueChanged = viewModel::setManagementCode,
                label = R.string.code,
                isError = isError,
                supportingText = stringResource(id = errorSupportingText),
            )

            Spacer(modifier = Modifier.padding(vertical = 8.dp))

            WappButton(
                onClick = { viewModel.checkManagementCode() },
                isEnabled = code.isNotBlank(),
                modifier = Modifier.padding(horizontal = 32.dp),
            )
        }
    }
}
