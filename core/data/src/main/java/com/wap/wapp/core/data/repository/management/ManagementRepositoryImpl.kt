package com.wap.wapp.core.data.repository.management

import com.wap.wapp.core.network.source.management.ManagementDataSource
import javax.inject.Inject

class ManagementRepositoryImpl @Inject constructor(
    private val managementDataSource: ManagementDataSource,
) : ManagementRepository {
    override suspend fun isManager(userId: String): Result<Boolean> =
        managementDataSource.isManager(userId)

    override suspend fun postManager(userId: String): Result<Unit> =
        managementDataSource.postManager(userId)

    override suspend fun getManagementCode(code: String): Result<Boolean> =
        managementDataSource.getManagementCode(code)

    override suspend fun deleteManager(userId: String): Result<Unit> =
        managementDataSource.deleteManager(userId)
}
