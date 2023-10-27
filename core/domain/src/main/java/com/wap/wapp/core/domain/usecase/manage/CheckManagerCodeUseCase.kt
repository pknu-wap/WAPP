package com.wap.wapp.core.domain.usecase.manage

import com.wap.wapp.core.data.repository.manage.ManageRepository
import com.wap.wapp.core.data.repository.user.UserRepository
import com.wap.wapp.core.domain.model.CodeVerification
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CheckManagerCodeUseCase @Inject constructor(
    private val manageRepository: ManageRepository,
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(code: String): Result<CodeVerification> {
        return runCatching {
            manageRepository.getManagerCode(code)
                .onSuccess { isVerified ->
                    if (isVerified.not()) { // 코드가 틀렸을 경우
                        return@runCatching CodeVerification.UNVERIFIED
                    }
                }

            // 운영진 등록
            val userId = userRepository.getUserId().getOrThrow()
            manageRepository.postManager(userId)
                .fold(
                    onSuccess = {
                        CodeVerification.VERIFIED
                    },
                    onFailure = { exception ->
                        throw(exception)
                    },
                )
        }
    }
}
