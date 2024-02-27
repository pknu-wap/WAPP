package com.wap.wapp.feature.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wap.wapp.core.domain.model.CodeValidation
import com.wap.wapp.core.domain.usecase.auth.ValidateWapMemberCodeUseCase
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
import com.wap.wapp.feature.auth.R

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val postUserProfileUseCase: PostUserProfileUseCase,
    private val validateWapMemberCodeUseCase: ValidateWapMemberCodeUseCase,
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

    private val _wapMemberCode: MutableStateFlow<String> = MutableStateFlow("")
    val wapMemberCode: StateFlow<String> = _wapMemberCode.asStateFlow()

    private val _isError: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isError: StateFlow<Boolean> get() = _isError

    private val _errorSupportingText: MutableStateFlow<Int> =
        MutableStateFlow(R.string.sign_up_dialog_hint)
    val errorSupportingText: StateFlow<Int> = _errorSupportingText.asStateFlow()

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
        validateWapMemberCodeUseCase(_wapMemberCode.value).onSuccess {
            when (it) {
                CodeValidation.VALID -> {
                    postUserProfileUseCase(
                        userName = _signUpName.value,
                        studentId = _signUpStudentId.value,
                        registeredAt = "${_signUpYear.value} ${_signUpSemester.value}",
                    ).onSuccess {
                        _signUpEventFlow.emit(SignUpEvent.SignUpSuccess)
                    }.onFailure { throwable ->
                        _signUpEventFlow.emit(SignUpEvent.Failure(throwable))
                        _isError.value = true
                    }
                }

                CodeValidation.INVALID -> {
                    _isError.value = true
                    _errorSupportingText.value = R.string.sign_up_incorrect_code
                }
            }
        }.onFailure { throwable ->
            _signUpEventFlow.emit(SignUpEvent.Failure(throwable))
            _isError.value = true
        }
    }

    fun isValidStudentId(): Boolean = (_signUpStudentId.value.length == STUDENT_ID_LENGTH)

    fun setName(name: String) { _signUpName.value = name }

    fun setStudentId(studentId: String) { _signUpStudentId.value = studentId }

    fun setYear(year: String) { _signUpYear.value = year }

    fun setSemester(semester: String) { _signUpSemester.value = semester }

    fun setWapMemberCode(code: String) { _wapMemberCode.value = code }

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
