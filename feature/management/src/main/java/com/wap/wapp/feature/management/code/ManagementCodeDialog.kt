package com.wap.wapp.feature.management.code

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.wap.designsystem.WappTheme
import com.wap.designsystem.component.WappButton
import com.wap.designsystem.component.WappTextField
import com.wap.wapp.feature.management.R
import com.wap.wapp.feature.management.code.ManagementCodeViewModel.ManagementCodeUiState
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageCodeDialog(
    viewModel: ManagementCodeViewModel = hiltViewModel(),
    onDismissRequest: () -> Unit,
    showToast: (Throwable) -> Unit,
) {
    LaunchedEffect(true) {
        viewModel.managementCodeUiState.collectLatest {
            when (it) {
                is ManagementCodeUiState.Success -> {
                    onDismissRequest()
                }
                is ManagementCodeUiState.Failure -> {
                    showToast(it.throwable)
                }
                is ManagementCodeUiState.Init -> { }
            }
        }
    }

    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        modifier = Modifier.padding(horizontal = 16.dp),
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
        ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Column {
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
            }

            WappTextField(
                value = viewModel.manageCode.collectAsState().value,
                onValueChanged = { code -> viewModel.setManagementCode(code = code) },
                label = R.string.code,
                isError = viewModel.isError.collectAsState().value,
                supportingText = stringResource(
                    id = viewModel.errorSupportingText.collectAsState().value,
                ),
            )

            WappButton(
                onClick = { viewModel.validateManageCode() },
                isEnabled = viewModel.manageCode
                    .collectAsState()
                    .value
                    .isNotBlank(),
            )
        }
    }
}

/*
@Preview
@Composable
fun previewDialog() {
    WappTheme {
        ManageCodeDialog()
    }
}
*/
