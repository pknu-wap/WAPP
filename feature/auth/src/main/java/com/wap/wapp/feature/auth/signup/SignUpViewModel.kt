package com.wap.wapp.feature.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wap.wapp.core.domain.usecase.user.PostUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val postUserProfileUseCase: PostUserProfileUseCase,
) : ViewModel() {

    private val _signUpEventFlow = MutableSharedFlow<SignUpEvent>()
    val signUpEventFlow: SharedFlow<SignUpEvent> get() = _signUpEventFlow

    private val _signUpName: MutableStateFlow<String> = MutableStateFlow("")
    val signUpName: StateFlow<String> get() = _signUpName

    private val _signUpStudentId: MutableStateFlow<String> = MutableStateFlow("")
    val signUpStudentId: StateFlow<String> get() = _signUpStudentId

    private val _signUpYear: MutableStateFlow<String> = MutableStateFlow("")
    val signUpYear: StateFlow<String> get() = _signUpYear

    private val _signUpSemester: MutableStateFlow<String> = MutableStateFlow("1학기")
    val signUpSemester: StateFlow<String> get() = _signUpSemester

    fun postUserProfile() {
        viewModelScope.launch {
            postUserProfileUseCase(
                userName = _signUpName.value,
                studentId = _signUpStudentId.value,
                registeredAt = "${_signUpYear.value} ${_signUpSemester.value}",
            ).onSuccess {
                _signUpEventFlow.emit(SignUpEvent.Success)
            }.onFailure { throwable ->
                _signUpEventFlow.emit(SignUpEvent.Failure(throwable))
            }
        }
    }

    fun setName(name: String) {
        _signUpName.value = name
    }

    fun setStudentId(studentId: String) {
        _signUpStudentId.value = studentId
    }

    fun setYear(year: String) {
        _signUpYear.value = year
    }

    fun setSemester(semester: String) {
        _signUpSemester.value = semester
    }

    sealed class SignUpEvent {
        data object Success : SignUpEvent()
        data class Failure(val throwable: Throwable) : SignUpEvent()
    }
}
