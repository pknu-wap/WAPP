package com.wap.wapp.core.domain.usecase.auth

import com.wap.wapp.core.domain.model.CodeValidation
import javax.inject.Inject

class ValidateWapMemberCodeUseCase @Inject constructor(
    private val signUpRepository: SignUpRepository,
) {
    suspend operator fun invoke(code: String): Result<CodeValidation> = runCatching {
        signUpRepository.validationWapCode(code).fold(
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
