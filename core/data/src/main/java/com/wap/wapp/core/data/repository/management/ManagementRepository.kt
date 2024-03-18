package com.wap.wapp.core.data.repository.management

interface ManagementRepository {
    suspend fun isManager(userId: String): Result<Boolean>

    suspend fun postManager(userId: String): Result<Unit>

    suspend fun checkManagementCode(code: String): Result<Boolean>

    suspend fun deleteManager(userId: String): Result<Unit>
}
