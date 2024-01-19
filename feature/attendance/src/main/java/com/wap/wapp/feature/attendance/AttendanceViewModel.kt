package com.wap.wapp.feature.attendance

import androidx.lifecycle.ViewModel
import com.wap.wapp.core.model.user.UserProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AttendanceViewModel @Inject constructor() : ViewModel() {
    private val _userProfile = MutableStateFlow<UserProfile>(DEFAULT_USER_PROFILE)
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

    companion object {
        val DEFAULT_USER_PROFILE = UserProfile("", "", "", "")
    }
}
