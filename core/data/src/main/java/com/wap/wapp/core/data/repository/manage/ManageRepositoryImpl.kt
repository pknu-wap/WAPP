package com.wap.wapp.core.data.repository.manage

import com.wap.wapp.core.network.source.manage.ManageDataSource
import javax.inject.Inject

class ManageRepositoryImpl @Inject constructor(
    private val manageDataSource: ManageDataSource,
) : ManageRepository {
    override suspend fun getManager(userId: String): Result<Boolean> {

    override suspend fun postManager(userId: String): Result<Unit> {
        return manageDataSource.postManager(userId)
    }

    override suspend fun getManageCode(code: String): Result<Boolean> {
        return manageDataSource.getManageCode(code)
}
