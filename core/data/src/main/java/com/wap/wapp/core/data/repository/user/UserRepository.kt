package com.wap.wapp.core.data.repository.user

import com.wap.wapp.core.model.user.UserProfile

interface UserRepository {
    suspend fun getUserProfile(userId: String): Result<UserProfile>

    suspend fun getUserId(): Result<String>

    suspend fun postUserProfile(
        userId: String,
        userName: String,
        studentId: String,
        registeredAt: String,
    ): Result<Unit>

    suspend fun deleteUserProfile(userId: String): Result<Unit>
}
