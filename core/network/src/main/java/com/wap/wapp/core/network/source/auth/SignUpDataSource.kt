package com.wap.wapp.core.network.source.auth

interface SignUpDataSource {
    suspend fun validationWapCode(code: String): Result<Boolean>
}
