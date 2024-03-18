package com.wap.wapp.core.network.model.user

import com.wap.wapp.core.model.user.UserProfile

data class UserProfileResponse(
    val userId: String,
    val userName: String,
    val studentId: String,
    val registeredAt: String,
) {
    constructor() : this(
        "",
        "",
        "",
        "",
    )

    fun toDomain(): UserProfile = UserProfile(
        userId = userId,
        userName = userName,
        studentId = studentId,
        registeredAt = registeredAt,
    )
}
