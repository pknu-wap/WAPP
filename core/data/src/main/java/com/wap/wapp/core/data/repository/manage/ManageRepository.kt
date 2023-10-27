package com.wap.wapp.core.data.repository.manage

interface ManageRepository {
    suspend fun getManager(userId: String): Result<Boolean>

    suspend fun postManager(userId: String): Result<Unit>

    suspend fun getManageCode(code: String): Result<Boolean>
}
