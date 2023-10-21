package com.wap.wapp.core.network.di

import com.wap.wapp.core.network.source.auth.AuthDataSource
import com.wap.wapp.core.network.source.auth.AuthDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
abstract class AuthDataSourceModule {
    @Binds
    @ActivityScoped
    abstract fun bindsAuthDataSource(
        authDataSourceImpl: AuthDataSourceImpl,
    ): AuthDataSource
}
