package com.wap.wapp.core.domain.usecase.management

import com.wap.wapp.core.data.repository.management.ManagementRepository
import com.wap.wapp.core.data.repository.user.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HasManagerStateUseCase @Inject constructor(
    private val managementRepository: ManagementRepository,
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(): Result<Boolean> = runCatching {
        val userId = userRepository.getUserId().getOrThrow()

        managementRepository.getManager(userId).fold(
            onSuccess = { hasManagerState -> hasManagerState },
            onFailure = { throw (it) },
        )
    }
}
