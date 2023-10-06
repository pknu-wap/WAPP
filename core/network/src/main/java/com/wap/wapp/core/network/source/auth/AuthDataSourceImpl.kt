package com.wap.wapp.core.network.source.auth

import android.app.Activity
import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider
import com.wap.wapp.core.network.utils.await
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    @ActivityContext private val context: Context
): AuthDataSource {
    override suspend fun hasPendingResult(): Boolean {
        return firebaseAuth.pendingAuthResult != null
    }

    override suspend fun signIn(email: String): Result<String> {
        return runCatching {
            val provider = OAuthProvider.newBuilder("github.com")
            provider.addCustomParameter("login", email)

            val activityContext = context as Activity

            val result = firebaseAuth.startActivityForSignInWithProvider(
                activityContext, provider.build()
            ).await()

            val user = checkNotNull(result.user)
            user.uid
        }
    }

    override suspend fun signUp(): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun signOut(): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun resign(): Result<Unit> {
        TODO("Not yet implemented")
    }
}
