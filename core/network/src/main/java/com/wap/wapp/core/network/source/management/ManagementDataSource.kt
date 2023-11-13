package com.wap.wapp.core.network.source.management

interface ManagementDataSource {
    suspend fun getManager(userId: String): Result<Boolean>

    suspend fun postManager(userId: String): Result<Unit>

    suspend fun getManagementCode(code: String): Result<Boolean>
}
