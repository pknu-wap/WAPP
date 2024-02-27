package com.wap.wapp.feature.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wap.wapp.core.domain.usecase.user.PostUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val postUserProfileUseCase: PostUserProfileUseCase,
) : ViewModel() {

    private val _signUpEventFlow = MutableSharedFlow<SignUpEvent>()
    val signUpEventFlow: SharedFlow<SignUpEvent> = _signUpEventFlow.asSharedFlow()

    private val _signUpName: MutableStateFlow<String> = MutableStateFlow("")
    val signUpName: StateFlow<String> = _signUpName.asStateFlow()

    private val _signUpStudentId: MutableStateFlow<String> = MutableStateFlow("")
    val signUpStudentId: StateFlow<String> = _signUpStudentId.asStateFlow()

    private val _signUpYear: MutableStateFlow<String> = MutableStateFlow("")
    val signUpYear: StateFlow<String> = _signUpYear.asStateFlow()

    private val _signUpSemester: MutableStateFlow<String> = MutableStateFlow(FIRST_SEMESTER)
    val signUpSemester: StateFlow<String> = _signUpSemester.asStateFlow()

    fun validationUserInformation() = viewModelScope.launch {
        if (!isValidStudentId()) {
            _signUpEventFlow.emit(
                SignUpEvent.Failure(IllegalStateException("학번은 9자리로만 입력하실 수 있어요!")),
            )
            return@launch
        }

        _signUpEventFlow.emit(SignUpEvent.ValidationSuccess)
    }

    fun postUserProfile() = viewModelScope.launch {
        if (!isValidStudentId()) {
            _signUpEventFlow.emit(
                SignUpEvent.Failure(IllegalStateException("학번은 9자리로만 입력하실 수 있어요!")),
            )
            return@launch
        }

        postUserProfileUseCase(
            userName = _signUpName.value,
            studentId = _signUpStudentId.value,
            registeredAt = "${_signUpYear.value} ${_signUpSemester.value}",
        ).onSuccess {
            _signUpEventFlow.emit(SignUpEvent.SignUpSuccess)
        }.onFailure { throwable ->
            _signUpEventFlow.emit(SignUpEvent.Failure(throwable))
        }
    }

    fun isValidStudentId(): Boolean = (_signUpStudentId.value.length == STUDENT_ID_LENGTH)

    fun setName(name: String) { _signUpName.value = name }

    fun setStudentId(studentId: String) { _signUpStudentId.value = studentId }

    fun setYear(year: String) { _signUpYear.value = year }

    fun setSemester(semester: String) { _signUpSemester.value = semester }

    sealed class SignUpEvent {
        data object ValidationSuccess : SignUpEvent()
        data object SignUpSuccess : SignUpEvent()
        data class Failure(val throwable: Throwable) : SignUpEvent()
    }

    companion object {
        const val FIRST_SEMESTER = "1학기"
        const val STUDENT_ID_LENGTH = 9
    }
}
