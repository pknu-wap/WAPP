package com.wap.wapp.core.domain.usecase.auth

import com.wap.wapp.core.data.repository.auth.AuthRepository
import com.wap.wapp.core.data.repository.user.UserRepository
import com.wap.wapp.core.domain.model.AuthState
import com.wap.wapp.core.domain.model.AuthState.SIGN_IN
import com.wap.wapp.core.domain.model.AuthState.SIGN_UP
import dagger.hilt.android.scopes.ActivityScoped
import java.lang.IllegalStateException
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
                .fold(
                    onFailure = { exception ->
                        when (exception) {
                            // 사용자를 조회할 수 없는 예외인 경우
                            is IllegalStateException -> {
                                SIGN_UP
                            }
                            else -> {
                                throw (exception)
                            }
                        }
                    },
                    onSuccess = {
                        SIGN_IN
                    },
                )
        }
    }
}
