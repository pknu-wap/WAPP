package com.wap.wapp.core.data.repository.manage

interface ManageRepository {
    suspend fun getManager(userId: String): Result<Unit>
}
