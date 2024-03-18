package com.wap.wapp.core.domain.usecase.user

import com.wap.wapp.core.data.repository.management.ManagementRepository
import com.wap.wapp.core.data.repository.user.UserRepository
import com.wap.wapp.core.model.user.UserRole
import javax.inject.Inject

class GetUserRoleUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val managementRepository: ManagementRepository,
) {
    suspend operator fun invoke(): Result<UserRole> =
        runCatching {
            val userId = userRepository.getUserId()
                .getOrElse { exception ->
                    if (exception is IllegalStateException) { // 회원이 아닌 경우
                        return@runCatching UserRole.GUEST
                    }
                    throw exception
                }

            managementRepository.isManager(userId)
                .fold(
                    onSuccess = { isManager ->
                        if (isManager) { // 매니저인 경우
                            return@fold UserRole.MANAGER
                        }
                        UserRole.MEMBER
                    },
                    onFailure = { exception ->
                        throw exception
                    },
                )
        }
}
