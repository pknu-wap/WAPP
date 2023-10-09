package com.wap.wapp.core.domain.auth

import com.wap.wapp.core.data.repository.auth.AuthRepository
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class SignInUseCase @Inject constructor(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke(email: String): Result<String> =
        repository.signIn(email)
}
