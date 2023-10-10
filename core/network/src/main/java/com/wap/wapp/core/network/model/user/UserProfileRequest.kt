package com.wap.wapp.core.network.model.user

data class UserProfileRequest(
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
}
