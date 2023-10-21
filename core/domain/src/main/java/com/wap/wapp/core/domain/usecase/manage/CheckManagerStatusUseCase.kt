package com.wap.wapp.core.domain.usecase.manage

import com.wap.wapp.core.data.repository.manage.ManageRepository
import com.wap.wapp.core.data.repository.user.UserRepository
import javax.inject.Inject

class CheckManagerStatusUseCase @Inject constructor(
    private val manageRepository: ManageRepository,
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(): Result<Unit> {
        return runCatching {
            val userId = userRepository.getUserId().getOrThrow()

            manageRepository.getManager(userId)
        }
    }
}
