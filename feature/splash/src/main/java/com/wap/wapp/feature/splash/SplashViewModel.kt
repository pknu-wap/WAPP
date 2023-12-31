package com.wap.wapp.feature.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {
    private val _eventFlow = MutableSharedFlow<SplashEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun event(event: SplashEvent) = viewModelScope.launch { _eventFlow.emit(event) }

    init {
        viewModelScope.launch {
            delay(2000)
            event(SplashEvent.TimerDone)
        }
    }

    sealed class SplashEvent {
        object TimerDone : SplashEvent()
    }
}
