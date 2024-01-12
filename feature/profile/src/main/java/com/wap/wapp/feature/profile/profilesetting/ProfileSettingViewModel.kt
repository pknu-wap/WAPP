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
    private val _eventFlow: MutableSharedFlow<ProfileSettingEvent> = MutableSharedFlow()
    val eventFlow: SharedFlow<ProfileSettingEvent> = _eventFlow.asSharedFlow()

    fun signOut() = viewModelScope.launch {
        signOutUseCase().onSuccess { }
            .onFailure { }
    }

    fun withdrawal(userId: String) = viewModelScope.launch {
        deleteUserUseCase(userId).onSuccess { }
            .onFailure { }
    }

    sealed class ProfileSettingEvent {
        data object Failure : ProfileSettingEvent()
        data object Success : ProfileSettingEvent()
    }
}
