package com.wap.wapp.core.network.source.manage

interface ManageDataSource {
    suspend fun getManager(userId: String): Result<Boolean>

    suspend fun postManager(userId: String): Result<Unit>

    suspend fun getManagerCode(code: String): Result<Boolean>
}
