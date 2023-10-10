package com.wap.wapp.core.data.repository.auth

import com.wap.wapp.core.network.source.auth.AuthDataSource
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
) : AuthRepository {
    override suspend fun hasPendingResult(): Boolean {
        return authDataSource.hasPendingResult()
    }

    override suspend fun signIn(email: String): Result<String> {
        return authDataSource.signIn(email)
    }

    override suspend fun signOut(): Result<Unit> {
        return authDataSource.signOut()
    }

    override suspend fun deleteUser(): Result<Unit> {
        return authDataSource.deleteUser()
    }
}
