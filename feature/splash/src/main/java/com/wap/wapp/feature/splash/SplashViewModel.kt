package com.wap.wapp.feature.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wap.wapp.core.domain.usecase.auth.IsUserSignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val isUserSignInUseCase: IsUserSignInUseCase,
) : ViewModel() {
    private val _splashUiEvent = MutableSharedFlow<SplashEvent>()
    val splashUiEvent = _splashUiEvent.asSharedFlow()

    private var _isIconLogoVisible = MutableStateFlow<Boolean>(false)
    var isIconLogoVisible = _isIconLogoVisible.asStateFlow()

    private var _isIconLogoGoUp = MutableStateFlow<Boolean>(false)
    var isIconLogoGoUp = _isIconLogoGoUp.asStateFlow()

    init {
        viewModelScope.launch {
            delay(800)
            _isIconLogoVisible.value = true
            delay(500)
            _isIconLogoGoUp.value = true
            delay(700)
            isUserSignIn()
        }
    }

    private suspend fun isUserSignIn() {
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

    sealed class SplashEvent {
        data object SignInUser : SplashEvent()
        data object NonSignInUser : SplashEvent()
        data class Failure(val throwable: Throwable) : SplashEvent()
    }
}
