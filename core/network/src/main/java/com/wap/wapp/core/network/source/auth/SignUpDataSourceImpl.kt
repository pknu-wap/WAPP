package com.wap.wapp.core.network.source.auth

import com.google.firebase.firestore.FirebaseFirestore
import com.wap.wapp.core.network.constant.CODES_COLLECTION
import com.wap.wapp.core.network.utils.await
import javax.inject.Inject

class SignUpDataSourceImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
) : SignUpDataSource {
    override suspend fun validationWapCode(code: String): Result<Boolean> = runCatching {
        val result = firebaseFirestore.collection(CODES_COLLECTION)
            .whereEqualTo("user", code)
            .get()
            .await()

        result.isEmpty.not()
    }
}
