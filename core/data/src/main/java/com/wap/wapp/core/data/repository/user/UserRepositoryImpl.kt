package com.wap.wapp.core.data.repository.user

import com.wap.wapp.core.model.user.UserProfile
import com.wap.wapp.core.network.model.user.UserProfileRequest
import com.wap.wapp.core.network.source.user.UserDataSource
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource,
) : UserRepository {
    override suspend fun getUserProfile(userId: String): Result<UserProfile> =
        userDataSource.getUserProfile(userId).mapCatching { response ->
            response.toDomain()
        }

    override suspend fun getUserId(): Result<String> = userDataSource.getUserId()

    override suspend fun postUserProfile(
        userId: String,
        userName: String,
        studentId: String,
        registeredAt: String,
    ): Result<Unit> = userDataSource.postUserProfile(
        UserProfileRequest(
            userId = userId,
            userName = userName,
            studentId = studentId,
            registeredAt = registeredAt,
        ),
    )
}
