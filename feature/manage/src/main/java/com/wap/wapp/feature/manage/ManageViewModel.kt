package com.wap.wapp.feature.manage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wap.wapp.core.domain.usecase.manage.HasManagerStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ManageViewModel @Inject constructor(
    private val hasManagerStateUseCase: HasManagerStateUseCase,
) : ViewModel() {

    private val _errorFlow: MutableSharedFlow<Throwable> = MutableSharedFlow()
    val errorFlow: SharedFlow<Throwable> get() = _errorFlow

    private val _managerState: MutableStateFlow<ManagerState> = MutableStateFlow(ManagerState.Init)
    val managerState: StateFlow<ManagerState> get() = _managerState

    init {
        hasManagerState()
    }

    private fun hasManagerState() {
        viewModelScope.launch {
            hasManagerStateUseCase()
                .onSuccess { hasManageState ->
                    if (hasManageState) {
                        _managerState.emit(ManagerState.Manager)
                    } else {
                        _managerState.emit(ManagerState.NonManager)
                    }
                }
                .onFailure { exception ->
                    _errorFlow.emit(exception)
                }
        }
    }

    sealed class ManagerState {
        data object Init : ManagerState()
        data object Manager : ManagerState()
        data object NonManager : ManagerState()
    }
}
