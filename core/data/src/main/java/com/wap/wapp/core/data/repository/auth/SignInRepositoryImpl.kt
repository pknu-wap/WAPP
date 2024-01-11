package com.wap.wapp.core.data.repository.auth

import com.wap.wapp.core.network.source.auth.SignInDataSource
import javax.inject.Inject

class SignInRepositoryImpl @Inject constructor(
    private val signInDataSource: SignInDataSource,
) : SignInRepository {
    override suspend fun hasPendingResult(): Boolean = signInDataSource.hasPendingResult()

    override suspend fun signIn(email: String): Result<String> = signInDataSource.signIn(email)
}
