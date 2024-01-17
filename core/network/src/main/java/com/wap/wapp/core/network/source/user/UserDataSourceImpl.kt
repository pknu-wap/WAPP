package com.wap.wapp.core.network.source.user

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.wap.wapp.core.network.constant.USER_COLLECTION
import com.wap.wapp.core.network.model.user.UserProfileRequest
import com.wap.wapp.core.network.model.user.UserProfileResponse
import com.wap.wapp.core.network.utils.await
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth,
) : UserDataSource {
    override suspend fun postUserProfile(
        userProfileRequest: UserProfileRequest,
    ): Result<Unit> = runCatching {
        val userId = userProfileRequest.userId
        val setOption = SetOptions.merge()

        firebaseFirestore.collection(USER_COLLECTION)
            .document(userId)
            .set(
                userProfileRequest,
                setOption,
            )
            .await()
    }

    override suspend fun getUserId(): Result<String> = runCatching {
        checkNotNull(firebaseAuth.uid)
    }

    override suspend fun getUserProfile(
        userId: String,
    ): Result<UserProfileResponse> = runCatching {
        val result = firebaseFirestore.collection(USER_COLLECTION)
            .document(userId)
            .get()
            .await()

        val userProfile = result.toObject(UserProfileResponse::class.java)
        checkNotNull(userProfile)
    }

    override suspend fun deleteUserProfile(userId: String): Result<Unit> = runCatching {
        firebaseFirestore.collection(USER_COLLECTION)
            .document(userId)
            .delete()
            .await()
    }
}
