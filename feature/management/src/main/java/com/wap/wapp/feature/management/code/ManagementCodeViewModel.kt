package com.wap.wapp.feature.management.code

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wap.wapp.core.domain.model.CodeValidation
import com.wap.wapp.core.domain.usecase.management.ValidateManagementCodeUseCase
import com.wap.wapp.feature.management.R
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ManagementCodeViewModel @Inject constructor(
    private val validateManagementCodeUseCase: ValidateManagementCodeUseCase,
) : ViewModel() {
    private val _managementCodeUiState: MutableStateFlow<ManagementCodeUiState> =
        MutableStateFlow(ManagementCodeUiState.Init)
    val managementCodeUiState: StateFlow<ManagementCodeUiState>
        get() = _managementCodeUiState

    private val _manageCode: MutableStateFlow<String> = MutableStateFlow("")
    val manageCode: StateFlow<String> get() = _manageCode

    private val _isError: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isError: StateFlow<Boolean> get() = _isError

    private val _errorSupportingText: MutableStateFlow<Int> =
        MutableStateFlow(R.string.management_dialog_hint)
    val errorSupportingText: StateFlow<Int> get() = _errorSupportingText

    fun validateManageCode() {
        viewModelScope.launch {
            validateManagementCodeUseCase(_manageCode.value)
                .onSuccess {
                    when (it) {
                        CodeValidation.VALID -> {
                            _managementCodeUiState.value = ManagementCodeUiState.Success
                        }
                        CodeValidation.INVALID -> {
                            _isError.value = true
                            _errorSupportingText.value = R.string.management_incorrect_code
                        }
                    }
                }
                .onFailure { throwable ->
                    _managementCodeUiState.value = ManagementCodeUiState.Failure(throwable)
                }
        }
    }

    fun setManagementCode(code: String) {
        _manageCode.value = code
    }

    sealed class ManagementCodeUiState {
        data object Init : ManagementCodeUiState()
        data object Success : ManagementCodeUiState()
        data class Failure(val throwable: Throwable) : ManagementCodeUiState()
    }
}
