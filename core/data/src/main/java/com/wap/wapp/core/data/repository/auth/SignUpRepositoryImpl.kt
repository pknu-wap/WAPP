package com.wap.wapp.core.data.repository.auth

import com.wap.wapp.core.network.source.auth.SignUpDataSource
import javax.inject.Inject

class SignUpRepositoryImpl @Inject constructor(
    private val signUpDataSource: SignUpDataSource,
) : SignUpRepository {
    override suspend fun validationWapCode(code: String): Result<Boolean> =
        signUpDataSource.validationWapCode(code)
}
