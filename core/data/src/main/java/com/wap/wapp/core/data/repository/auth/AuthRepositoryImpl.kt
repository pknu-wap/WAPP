package com.wap.wapp.core.data.repository.auth

import com.wap.wapp.core.network.source.auth.AuthDataSource
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
) : AuthRepository {
    override suspend fun signOut(): Result<Unit> = authDataSource.signOut()

    override suspend fun deleteUser(): Result<Unit> = authDataSource.deleteUser()

    override suspend fun isUserSignIn(): Result<Boolean> = authDataSource.isUserSignIn()

    override suspend fun validationWapCode(code: String): Result<Boolean> =
        authDataSource.validationWapCode(code)
}
