package com.wap.wapp.core.network.source.manage

import com.google.firebase.firestore.FirebaseFirestore
import com.wap.wapp.core.network.constant.MANAGER_COLLECTION
import com.wap.wapp.core.network.utils.await
import javax.inject.Inject

class ManageDataSourceImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
) : ManageDataSource {
    override suspend fun getManager(userId: String): Result<Unit> {
        return runCatching {
            firebaseFirestore.collection(MANAGER_COLLECTION)
                .document(userId)
                .get()
                .await()
        }
    }
}
