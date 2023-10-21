package com.wap.wapp.core.domain.usecase.manage

import com.wap.wapp.core.data.repository.manage.ManageRepository
import com.wap.wapp.core.data.repository.user.UserRepository
import javax.inject.Inject

class CheckManagerCodeUseCase @Inject constructor(
    private val manageRepository: ManageRepository,
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(code: String): Result<Unit> {
        return runCatching {
            // 운영진 코드 검증
            manageRepository.postManagerCode(code).getOrThrow()

            // 운영진 등록
            val userId = userRepository.getUserId().getOrThrow()
            manageRepository.postManager(userId)
        }
    }
}
