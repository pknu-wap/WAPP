package com.wap.wapp.core.data.repository.manage

interface ManageRepository {
    suspend fun getManager(userId: String): Result<Unit>

    suspend fun postManager(userId: String): Result<Unit>

    suspend fun postManagerCode(code: String): Result<Unit>
}
