package com.wap.wapp.core.data.di

import com.wap.wapp.core.data.repository.management.ManagementRepository
import com.wap.wapp.core.data.repository.management.ManagementRepositoryImpl
import com.wap.wapp.core.data.repository.user.UserRepository
import com.wap.wapp.core.data.repository.user.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    @Singleton
    abstract fun bindsUserRepository(
        userRepositoryImpl: UserRepositoryImpl,
    ): UserRepository

    @Binds
    @Singleton
    abstract fun bindsManagementRepository(
        managementRepositoryImpl: ManagementRepositoryImpl,
    ): ManagementRepository
}
