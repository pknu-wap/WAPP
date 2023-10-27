package com.wap.wapp.core.network.source.manage

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.wap.wapp.core.network.constant.CODES_COLLECTION
import com.wap.wapp.core.network.constant.MANAGER_COLLECTION
import com.wap.wapp.core.network.utils.await
import javax.inject.Inject

class ManageDataSourceImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
) : ManageDataSource {
    override suspend fun getManager(userId: String): Result<Unit> {
        return runCatching {
            val result = firebaseFirestore.collection(MANAGER_COLLECTION)
                .whereEqualTo("userId", userId)
                .get()
                .await()

            check(result.isEmpty.not())
        }
    }

    override suspend fun postManager(userId: String): Result<Unit> {
        return runCatching {
            val userIdMap = mapOf("userId" to userId)
            val setOption = SetOptions.merge()

            firebaseFirestore.collection(MANAGER_COLLECTION)
                .document(userId)
                .set(userIdMap, setOption)
                .await()
        }
    }

    override suspend fun getManagerCode(code: String): Result<Boolean> {
        return runCatching {
            val result = firebaseFirestore.collection(CODES_COLLECTION)
                .whereEqualTo("manager", code)
                .get()
                .await()

            result.isEmpty.not()
        }
    }
}
