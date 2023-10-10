package com.wap.wapp.core.network.source.user

import com.wap.wapp.core.network.model.user.UserProfileResponse

interface UserDataSource {
    suspend fun getUserProfile(userId: String): Result<UserProfileResponse>
}
