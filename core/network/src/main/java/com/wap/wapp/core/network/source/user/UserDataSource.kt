package com.wap.wapp.core.network.source.user

import com.wap.wapp.core.network.model.user.UserProfileRequest
import com.wap.wapp.core.network.model.user.UserProfileResponse

interface UserDataSource {
    suspend fun postUserProfile(userProfileRequest: UserProfileRequest): Result<Unit>

    suspend fun getUserProfile(userId: String): Result<UserProfileResponse>
}
