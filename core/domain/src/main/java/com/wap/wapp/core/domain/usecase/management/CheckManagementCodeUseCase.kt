package com.wap.wapp.core.domain.usecase.management

import com.wap.wapp.core.data.repository.management.ManagementRepository
import com.wap.wapp.core.data.repository.user.UserRepository
import com.wap.wapp.core.domain.model.CodeValidation
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CheckManagementCodeUseCase @Inject constructor(
    private val managementRepository: ManagementRepository,
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(code: String): Result<CodeValidation> = runCatching {
        managementRepository.checkManagementCode(code)
            .onSuccess { isValid ->
                if (isValid.not()) { // 코드가 틀렸을 경우
                    return@runCatching CodeValidation.INVALID
                }
            }

        // 운영진 등록
        val userId = userRepository.getUserId().getOrThrow()
        managementRepository.postManager(userId).fold(
            onSuccess = { CodeValidation.VALID },
            onFailure = { exception -> throw (exception) },
        )
    }
}
