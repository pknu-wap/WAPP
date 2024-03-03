package com.wap.wapp.core.domain.usecase.auth

import com.wap.wapp.core.data.repository.auth.AuthRepository
import com.wap.wapp.core.domain.model.CodeValidation
import javax.inject.Inject

class CheckMemberCodeUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(code: String): Result<CodeValidation> = runCatching {
        authRepository.checkMemberCode(code).fold(
            onSuccess = { isValid ->
                if (isValid) {
                    return@fold CodeValidation.VALID
                }
                CodeValidation.INVALID
            },
            onFailure = { CodeValidation.INVALID },
        )
    }
}
