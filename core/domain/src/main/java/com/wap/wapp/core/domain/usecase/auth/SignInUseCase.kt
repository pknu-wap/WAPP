package com.wap.wapp.core.domain.usecase.auth

import com.wap.wapp.core.data.repository.auth.AuthRepository
import com.wap.wapp.core.data.repository.user.UserRepository
import com.wap.wapp.core.domain.model.AuthState
import com.wap.wapp.core.domain.model.AuthState.SIGN_IN
import com.wap.wapp.core.domain.model.AuthState.SIGN_UP
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class SignInUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(email: String): Result<AuthState> {
        return runCatching {
            val userId = authRepository.signIn(email)
                .getOrThrow()

            userRepository.getUserProfile(userId)
                .onFailure { exception ->
                    // 만약 사용자를 찾을 수 없는 경우, 회원가입
                    val userNotFoundException = IllegalStateException()
                    if (exception == userNotFoundException) {
                        return Result.success(SIGN_UP)
                    }
                }
            // 사용자를 찾은 경우, 로그인
            SIGN_IN
        }
    }
}
