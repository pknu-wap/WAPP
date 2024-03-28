package com.wap.wapp.feature.auth.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wap.wapp.core.analytics.AnalyticsHelper
import com.wap.wapp.core.commmon.extensions.logUserSignedIn
import com.wap.wapp.core.domain.usecase.user.GetUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val analyticsHelper: AnalyticsHelper,
) : ViewModel() {
    fun logUserSignedIn() = viewModelScope.launch {
        getUserProfileUseCase()
            .onSuccess { userProfile ->
                analyticsHelper.logUserSignedIn(
                    userId = userProfile.userId,
                    userName = userProfile.userName,
                )
            }
    }
}
