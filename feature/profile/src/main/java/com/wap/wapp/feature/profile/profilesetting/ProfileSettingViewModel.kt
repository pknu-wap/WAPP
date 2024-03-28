package com.wap.wapp.feature.profile.profilesetting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wap.wapp.core.analytics.AnalyticsEvent
import com.wap.wapp.core.analytics.AnalyticsHelper
import com.wap.wapp.core.domain.usecase.auth.DeleteUserUseCase
import com.wap.wapp.core.domain.usecase.auth.SignOutUseCase
import com.wap.wapp.core.domain.usecase.user.GetUserProfileUseCase
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
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val analyticsHelper: AnalyticsHelper,
) : ViewModel() {
    private val _eventFlow: MutableSharedFlow<EventResult> = MutableSharedFlow()
    val eventFlow: SharedFlow<EventResult> = _eventFlow.asSharedFlow()

    fun signOut() = viewModelScope.launch {
        signOutUseCase()
            .onSuccess {
                logUserSignedOut()
                _eventFlow.emit(EventResult.Success)
            }
            .onFailure { _eventFlow.emit(EventResult.Failure(it)) }
    }

    fun withdrawal(userId: String) = viewModelScope.launch {
        deleteUserUseCase(userId)
            .onSuccess {
                _eventFlow.emit(EventResult.Success)
            }
            .onFailure { _eventFlow.emit(EventResult.Failure(it)) }
    }

    private fun logUserSignedOut() = viewModelScope.launch {
        getUserProfileUseCase()
            .onSuccess { userProfile ->
                analyticsHelper.logEvent(
                    AnalyticsEvent(
                        type = "signed_out",
                        extras = listOf(
                            AnalyticsEvent.Param(
                                key = "user_id",
                                value = userProfile.userId,
                            ),
                            AnalyticsEvent.Param(
                                key = "user_name",
                                value = userProfile.userName,
                            ),
                        ),
                    ),
                )
            }
    }

    sealed class EventResult {
        data class Failure(val throwable: Throwable) : EventResult()
        data object Success : EventResult()
    }
}
