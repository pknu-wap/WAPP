package com.wap.wapp.core.network.di

import com.wap.wapp.core.network.source.manage.ManageDataSource
import com.wap.wapp.core.network.source.manage.ManageDataSourceImpl
import com.wap.wapp.core.network.source.user.UserDataSource
import com.wap.wapp.core.network.source.user.UserDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    @Binds
    @Singleton
    abstract fun bindsUserDataSource(
        userDataSourceImpl: UserDataSourceImpl,
    ): UserDataSource

    @Binds
    @Singleton
    abstract fun bindsManageDataSource(
        manageDataSourceImpl: ManageDataSourceImpl,
    ): ManageDataSource
}
