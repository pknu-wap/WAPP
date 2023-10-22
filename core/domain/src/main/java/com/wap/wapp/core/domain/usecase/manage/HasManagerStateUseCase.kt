package com.wap.wapp.core.domain.usecase.manage

import com.wap.wapp.core.data.repository.manage.ManageRepository
import com.wap.wapp.core.data.repository.user.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HasManagerStateUseCase @Inject constructor(
    private val manageRepository: ManageRepository,
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(): Result<Boolean> {
        return runCatching {
            val userId = userRepository.getUserId().getOrThrow()

            manageRepository.getManager(userId).fold(
                onSuccess = { hasManagerState -> hasManagerState },
                onFailure = { throw(it) },
            )
        }
    }
}
