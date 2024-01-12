package com.wap.wapp.feature.profile.profilesetting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wap.wapp.core.domain.usecase.auth.DeleteUserUseCase
import com.wap.wapp.core.domain.usecase.auth.SignOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileSettingViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
) : ViewModel() {
    private val _eventFlow: MutableSharedFlow<EventResult> = MutableSharedFlow()
    val eventFlow: SharedFlow<EventResult> = _eventFlow.asSharedFlow()

    fun signOut() = viewModelScope.launch {
        signOutUseCase().onSuccess { _eventFlow.emit(EventResult.Success) }
            .onFailure { _eventFlow.emit(EventResult.Failure(it)) }
    }

    fun withdrawal(userId: String) = viewModelScope.launch {
        deleteUserUseCase(userId).onSuccess { _eventFlow.emit(EventResult.Success) }
            .onFailure { _eventFlow.emit(EventResult.Failure(it)) }
    }

    sealed class EventResult {
        data class Failure(val throwable: Throwable) : EventResult()
        data object Success : EventResult()
    }
}
