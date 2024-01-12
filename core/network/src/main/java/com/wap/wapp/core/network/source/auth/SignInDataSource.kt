package com.wap.wapp.core.network.source.auth

interface SignInDataSource {
    suspend fun signIn(email: String): Result<String>
}
