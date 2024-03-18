package com.wap.wapp.core.data.repository.auth

interface SignInRepository {
    suspend fun signIn(email: String): Result<String>
}
