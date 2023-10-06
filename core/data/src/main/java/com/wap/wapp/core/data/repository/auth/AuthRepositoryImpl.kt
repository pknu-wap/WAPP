package com.wap.wapp.core.data.repository.auth

import com.wap.wapp.core.network.source.auth.AuthDataSource
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource
): AuthRepository {
    override suspend fun hasPendingResult(): Boolean {
        return authDataSource.hasPendingResult()
    }

    override suspend fun signIn(email: String): Result<String> {
        return authDataSource.signIn(email)
    }
}