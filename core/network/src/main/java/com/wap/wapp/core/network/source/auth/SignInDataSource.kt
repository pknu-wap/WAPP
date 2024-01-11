package com.wap.wapp.core.network.source.auth

interface SignInDataSource {
    suspend fun hasPendingResult(): Boolean

    suspend fun signIn(email: String): Result<String>
}
