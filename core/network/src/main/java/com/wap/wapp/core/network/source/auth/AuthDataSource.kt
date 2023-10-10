package com.wap.wapp.core.network.source.auth

interface AuthDataSource {
    suspend fun hasPendingResult(): Boolean

    suspend fun signIn(email: String): Result<String>

    suspend fun signOut(): Result<Unit>

    suspend fun deleteUser(): Result<Unit>
}
