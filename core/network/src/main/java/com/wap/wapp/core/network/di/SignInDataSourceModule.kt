package com.wap.wapp.core.network.di

import com.wap.wapp.core.network.source.auth.SignInDataSource
import com.wap.wapp.core.network.source.auth.SignInDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
abstract class SignInDataSourceModule {
    @Binds
    @ActivityScoped
    abstract fun bindsSignInDataSource(
        signInDataSourceImpl: SignInDataSourceImpl,
    ): SignInDataSource
}
