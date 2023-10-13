package com.wap.wapp.core.domain.usecase.auth

import com.wap.wapp.core.data.repository.auth.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteUserUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(): Result<Unit> =
        authRepository.deleteUser()
}
