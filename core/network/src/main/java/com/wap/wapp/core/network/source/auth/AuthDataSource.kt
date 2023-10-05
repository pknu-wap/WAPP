package com.wap.wapp.core.network.source.auth

import com.wap.wapp.core.network.model.auth.SignUpRequest

interface AuthDataSource {
    suspend fun hasPendingResult(): Boolean

    suspend fun signIn(email: String): Result<String>

    suspend fun signUp(signUpRequest: SignUpRequest): Result<Unit>

    suspend fun signOut(): Result<Unit>

    suspend fun resign(): Result<Unit>
}