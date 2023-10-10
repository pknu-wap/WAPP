package com.wap.wapp.core.data.repository.auth

interface AuthRepository {
    suspend fun hasPendingResult(): Boolean

    suspend fun signIn(email: String): Result<String>

    suspend fun signOut(): Result<Unit>

    suspend fun deleteUser(): Result<Unit>
}
