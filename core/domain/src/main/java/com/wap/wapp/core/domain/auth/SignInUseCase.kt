package com.wap.wapp.core.domain.auth

import com.wap.wapp.core.data.repository.auth.AuthRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val repository: AuthRepository
){
    suspend operator fun invoke(email: String): Result<String> =
        repository.signIn(email)
}