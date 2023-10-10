package com.wap.wapp.core.network.model.auth

data class SignUpRequest(
    val userId: String,
    val userName: String,
    val studentId: String,
    val registeredAt: String,
)
