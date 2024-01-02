package com.wap.wapp.core.data.repository.management

import com.wap.wapp.core.network.source.management.ManagementDataSource
import javax.inject.Inject

class ManagementRepositoryImpl @Inject constructor(
    private val managementDataSource: ManagementDataSource,
) : ManagementRepository {
    override suspend fun getManager(userId: String): Result<Boolean> =
        managementDataSource.getManager(userId)

    override suspend fun postManager(userId: String): Result<Unit> =
        managementDataSource.postManager(userId)

    override suspend fun getManagementCode(code: String): Result<Boolean> =
        managementDataSource.getManagementCode(code)
}
