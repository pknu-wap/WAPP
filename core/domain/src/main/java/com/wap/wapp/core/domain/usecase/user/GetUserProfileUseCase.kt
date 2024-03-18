package com.wap.wapp.core.domain.usecase.user

import com.wap.wapp.core.data.repository.user.UserRepository
import com.wap.wapp.core.model.user.UserProfile
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(): Result<UserProfile> = runCatching {
        val userId = userRepository.getUserId().getOrThrow()

        userRepository.getUserProfile(userId).fold(
            onSuccess = { userProfile -> userProfile },
            onFailure = { throw it },
        )
    }
}
