package com.wap.wapp.core.domain.usecase.user

import com.wap.wapp.core.data.repository.user.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostUserProfileUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(
        userName: String,
        studentId: String,
        registeredAt: String,
    ): Result<Unit> {
        return runCatching {
            val userId = userRepository.getUserId().getOrThrow()

            userRepository.postUserProfile(
                userId = userId,
                userName = userName,
                studentId = studentId,
                registeredAt = registeredAt,
            )
        }
    }
}
