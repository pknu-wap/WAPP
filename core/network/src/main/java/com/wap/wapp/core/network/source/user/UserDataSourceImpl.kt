package com.wap.wapp.core.network.source.user

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.wap.wapp.core.network.constant.USER_COLLECTION
import com.wap.wapp.core.network.model.user.UserProfileRequest
import com.wap.wapp.core.network.model.user.UserProfileResponse
import com.wap.wapp.core.network.utils.await
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
) : UserDataSource {
    override suspend fun postUserProfile(
        userProfile: UserProfileRequest,
    ): Result<Unit> {
        return runCatching {
            val userId = userProfile.userId
            val setOption = SetOptions.merge()

            firebaseFirestore.collection(USER_COLLECTION)
                .document(userId)
                .set(
                    userProfile,
                    setOption,
                )
                .await()
        }
    }

    override suspend fun getUserProfile(
        userId: String,
    ): Result<UserProfileResponse> {
        return runCatching {
            val result = firebaseFirestore.collection(USER_COLLECTION)
                .document(userId)
                .get()
                .await()

            val userProfile = result.toObject(UserProfileResponse::class.java)
            checkNotNull(userProfile)
        }
    }
}
