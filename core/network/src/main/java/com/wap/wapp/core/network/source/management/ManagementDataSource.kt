package com.wap.wapp.core.network.source.management

interface ManagementDataSource {
    suspend fun isManager(userId: String): Result<Boolean>

    suspend fun postManager(userId: String): Result<Unit>

    suspend fun checkManagementCode(code: String): Result<Boolean>

    suspend fun deleteManager(userId: String): Result<Unit>
}
