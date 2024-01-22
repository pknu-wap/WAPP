package com.wap.wapp.core.network.source.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.wap.wapp.core.network.utils.await
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) : AuthDataSource {
    override suspend fun signOut(): Result<Unit> = runCatching {
        firebaseAuth.signOut()
    }

    override suspend fun deleteUser(): Result<Unit> = runCatching {
        val user = checkNotNull(firebaseAuth.currentUser)

        user.delete()
            .await()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun isUserSignIn(): Result<Boolean> = runCatching {
        suspendCancellableCoroutine { cont ->
            val authStateListener = object : AuthStateListener {
                override fun onAuthStateChanged(remoteAuth: FirebaseAuth) {
                    val user = remoteAuth.currentUser
                    if (user != null) {
                        cont.resume(true, null)
                    } else {
                        cont.resume(false, null)
                    }

                    // 한 번 호출된 후에 Listener 삭제
                    firebaseAuth.removeAuthStateListener(this)
                }
            }

            firebaseAuth.addAuthStateListener(authStateListener)

            cont.invokeOnCancellation { // Coroutine이 취소되는 경우 리스너 삭제
                firebaseAuth.removeAuthStateListener(authStateListener)
            }
        }
    }
}
