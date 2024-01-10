package com.wap.wapp.feature.survey

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wap.wapp.core.domain.usecase.survey.GetSurveyFormListUseCase
import com.wap.wapp.core.domain.usecase.survey.IsSubmittedSurveyUseCase
import com.wap.wapp.core.domain.usecase.user.GetUserRoleUseCase
import com.wap.wapp.core.model.survey.SurveyForm
import com.wap.wapp.core.model.user.UserRole
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SurveyViewModel @Inject constructor(
    private val getUserRoleUseCase: GetUserRoleUseCase,
    private val getSurveyFormListUseCase: GetSurveyFormListUseCase,
    private val isSubmittedSurveyUseCase: IsSubmittedSurveyUseCase,
) : ViewModel() {
    private val _surveyFormListUiState: MutableStateFlow<SurveyFormListUiState> =
        MutableStateFlow(SurveyFormListUiState.Init)
    val surveyFormListUiState = _surveyFormListUiState.asStateFlow()

    private val _surveyEvent: MutableSharedFlow<SurveyUiEvent> = MutableSharedFlow()
    val surveyEvent = _surveyEvent.asSharedFlow()

    private val _userRoleUiState: MutableStateFlow<UserRoleUiState> =
        MutableStateFlow(UserRoleUiState.Init)
    val userRoleUiState = _userRoleUiState.asStateFlow()

    init {
        getUserRole()
    }

    private fun getUserRole() {
        viewModelScope.launch {
            getUserRoleUseCase()
                .onSuccess { userRole ->
                    _userRoleUiState.value = UserRoleUiState.Success(userRole)
                }
                .onFailure { throwable ->
                    _surveyEvent.emit(SurveyUiEvent.Failure(throwable))
                }
        }
    }

    fun getSurveyFormList() {
        viewModelScope.launch {
            getSurveyFormListUseCase()
                .onSuccess { surveyFormList ->
                    _surveyFormListUiState.value = SurveyFormListUiState.Success(surveyFormList)
                }
                .onFailure { throwable ->
                    _surveyEvent.emit(SurveyUiEvent.Failure(throwable))
                }
        }
    }

    fun isSubmittedSurvey(surveyFormId: String) {
        viewModelScope.launch {
            isSubmittedSurveyUseCase(surveyFormId)
                .onSuccess { isSubmittedSurvey ->
                    if (isSubmittedSurvey) {
                        _surveyEvent.emit(SurveyUiEvent.AlreadySubmitted)
                    } else {
                        _surveyEvent.emit(SurveyUiEvent.NotSubmitted(surveyFormId))
                    }
                }
                .onFailure { throwable ->
                    _surveyEvent.emit(SurveyUiEvent.Failure(throwable))
                }
        }
    }

    sealed class UserRoleUiState {
        data object Init : UserRoleUiState()
        data class Success(val userRole: UserRole) : UserRoleUiState()
    }

    sealed class SurveyFormListUiState {
        data object Init : SurveyFormListUiState()
        data class Success(val surveyFormList: List<SurveyForm>) : SurveyFormListUiState()
    }

    sealed class SurveyUiEvent {
        data class Failure(val throwable: Throwable) : SurveyUiEvent()
        data object AlreadySubmitted : SurveyUiEvent()
        data class NotSubmitted(val surveyFormId: String) : SurveyUiEvent()
    }
}
