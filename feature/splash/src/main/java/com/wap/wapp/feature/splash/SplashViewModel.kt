package com.wap.wapp.feature.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wap.wapp.core.domain.usecase.auth.IsUserSignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val isUserSignInUseCase: IsUserSignInUseCase,
) : ViewModel() {
    private val _splashUiEvent = MutableSharedFlow<SplashEvent>()
    val splashUiEvent = _splashUiEvent.asSharedFlow()

    init {
        viewModelScope.launch {
            delay(2000)
            isUserSignIn()
        }
    }

    private fun isUserSignIn() {
        viewModelScope.launch {
            isUserSignInUseCase()
                .onSuccess { isSignIn ->
                    if (isSignIn) {
                        _splashUiEvent.emit(SplashEvent.SignInUser)
                    } else {
                        _splashUiEvent.emit(SplashEvent.NonSignInUser)
                    }
                }.onFailure { throwable ->
                    _splashUiEvent.emit(SplashEvent.Failure(throwable))
                }
        }
    }

    sealed class SplashEvent {
        data object SignInUser : SplashEvent()
        data object NonSignInUser : SplashEvent()
        data class Failure(val throwable: Throwable) : SplashEvent()
    }
}
