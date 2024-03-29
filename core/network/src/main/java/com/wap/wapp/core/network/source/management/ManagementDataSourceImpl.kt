package com.wap.wapp.core.network.source.management

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.wap.wapp.core.network.constant.CODES_COLLECTION
import com.wap.wapp.core.network.constant.MANAGER_COLLECTION
import com.wap.wapp.core.network.utils.await
import javax.inject.Inject

class ManagementDataSourceImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
) : ManagementDataSource {
    override suspend fun isManager(userId: String): Result<Boolean> = runCatching {
        val result = firebaseFirestore.collection(MANAGER_COLLECTION)
            .whereEqualTo("userId", userId)
            .get()
            .await()

        result.isEmpty.not()
    }

    override suspend fun postManager(userId: String): Result<Unit> = runCatching {
        val userIdMap = mapOf("userId" to userId)
        val setOption = SetOptions.merge()

        firebaseFirestore.collection(MANAGER_COLLECTION)
            .document(userId)
            .set(userIdMap, setOption)
            .await()
    }

    override suspend fun checkManagementCode(code: String): Result<Boolean> = runCatching {
        val result = firebaseFirestore.collection(CODES_COLLECTION)
            .whereEqualTo("management", code)
            .get()
            .await()

        result.isEmpty.not()
    }

    override suspend fun deleteManager(userId: String): Result<Unit> = runCatching {
        firebaseFirestore.collection(MANAGER_COLLECTION)
            .document(userId)
            .delete()
            .await()
    }
}
