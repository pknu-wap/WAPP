package com.wap.wapp.core.data.repository.auth

interface AuthRepository {
    suspend fun signOut(): Result<Unit>

    suspend fun deleteUser(): Result<Unit>

    suspend fun isUserSignIn(): Result<Boolean>

    suspend fun validateMemberCode(code: String): Result<Boolean>
}
