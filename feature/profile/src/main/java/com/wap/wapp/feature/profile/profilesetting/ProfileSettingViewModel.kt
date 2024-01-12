package com.wap.wapp.feature.profile.profilesetting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wap.wapp.core.domain.usecase.auth.DeleteUserUseCase
import com.wap.wapp.core.domain.usecase.auth.SignOutUseCase
import com.wap.wapp.core.model.user.UserProfile
import com.wap.wapp.feature.profile.ProfileViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileSettingViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
) : ViewModel() {

    private val _userProfile = MutableStateFlow<UserProfile>(ProfileViewModel.DEFAULT_USER_PROFILE)
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

    fun signOut() = viewModelScope.launch {
        signOutUseCase().onSuccess { }
            .onFailure { }
    }

    fun withdrawal() = viewModelScope.launch {
        deleteUserUseCase(_userProfile.value.userId).onSuccess { }
            .onFailure { }
    }
}
