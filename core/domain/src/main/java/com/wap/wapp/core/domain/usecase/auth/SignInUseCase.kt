package com.wap.wapp.core.domain.usecase.auth

import com.wap.wapp.core.data.repository.auth.SignInRepository
import com.wap.wapp.core.data.repository.user.UserRepository
import com.wap.wapp.core.domain.model.AuthState
import com.wap.wapp.core.domain.model.AuthState.SIGN_IN
import com.wap.wapp.core.domain.model.AuthState.SIGN_UP
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val signInRepository: SignInRepository,
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(email: String): Result<AuthState> = runCatching {
        val userId = signInRepository.signIn(email)
            .getOrThrow()

        userRepository.getUserProfile(userId)
            .fold(
                onFailure = { exception ->
                    // 등록되지 않은 사용자인 경우
                    if (exception is IllegalStateException) {
                        return@fold SIGN_UP
                    }
                    // 그 외의 예외인 경우
                    throw (exception)
                },
                onSuccess = { SIGN_IN },
            )
    }
}
