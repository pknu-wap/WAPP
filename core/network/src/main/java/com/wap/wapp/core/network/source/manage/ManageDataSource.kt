package com.wap.wapp.core.network.source.manage

interface ManageDataSource {
    suspend fun getManager(userId: String): Result<Unit>
}
