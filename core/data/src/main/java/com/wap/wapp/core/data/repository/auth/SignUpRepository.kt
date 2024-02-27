package com.wap.wapp.core.data.repository.auth

interface SignUpRepository {
    suspend fun validationWapCode(code: String): Result<Boolean>
}
