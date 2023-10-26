package com.wap.wapp.core.data.repository.manage

import com.wap.wapp.core.network.source.manage.ManageDataSource
import javax.inject.Inject

class ManageRepositoryImpl @Inject constructor(
    private val manageDataSource: ManageDataSource,
) : ManageRepository {
    override suspend fun getManager(userId: String): Result<Unit> {
        return manageDataSource.getManager(userId)
    }

    override suspend fun postManager(userId: String): Result<Unit> {
        return manageDataSource.postManager(userId)
    }

    override suspend fun getManagerCode(code: String): Result<Boolean> {
        return manageDataSource.getManagerCode(code)
    }
}
