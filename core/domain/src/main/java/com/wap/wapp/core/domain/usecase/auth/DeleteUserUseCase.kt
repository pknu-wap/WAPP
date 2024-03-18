package com.wap.wapp.core.domain.usecase.auth

import com.wap.wapp.core.data.repository.auth.AuthRepository
import com.wap.wapp.core.data.repository.management.ManagementRepository
import com.wap.wapp.core.data.repository.user.UserRepository
import com.wap.wapp.core.domain.usecase.user.GetUserRoleUseCase
import com.wap.wapp.core.model.user.UserRole
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteUserUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val managementRepository: ManagementRepository,
    private val getUserRoleUseCase: GetUserRoleUseCase,
) {
    suspend operator fun invoke(userId: String): Result<Unit> = runCatching {
        val userRole = getUserRoleUseCase().getOrThrow()
        when (userRole) {
            UserRole.GUEST -> { return@runCatching }

            UserRole.MEMBER -> {
                userRepository.deleteUserProfile(userId)
            }

            UserRole.MANAGER -> {
                userRepository.deleteUserProfile(userId)
                managementRepository.deleteManager(userId)
            }
        }

        authRepository.deleteUser().getOrThrow()
    }
}
