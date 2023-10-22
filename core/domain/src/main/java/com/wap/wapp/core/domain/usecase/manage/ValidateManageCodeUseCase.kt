package com.wap.wapp.core.domain.usecase.manage

import com.wap.wapp.core.data.repository.manage.ManageRepository
import com.wap.wapp.core.data.repository.user.UserRepository
import com.wap.wapp.core.domain.model.CodeValidation
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ValidateManageCodeUseCase @Inject constructor(
    private val manageRepository: ManageRepository,
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(code: String): Result<CodeValidation> {
        return runCatching {
            manageRepository.getManageCode(code)
                .onSuccess { isValid ->
                    if (isValid.not()) { // 코드가 틀렸을 경우
                        return@runCatching CodeValidation.INVALID
                    }
                }

            // 운영진 등록
            val userId = userRepository.getUserId().getOrThrow()
            manageRepository.postManager(userId)
                .fold(
                    onSuccess = {
                        CodeValidation.VALID
                    },
                    onFailure = { exception ->
                        throw(exception)
                    },
                )
        }
    }
}
