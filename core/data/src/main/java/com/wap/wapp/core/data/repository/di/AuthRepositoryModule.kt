package com.wap.wapp.core.data.repository.di

import com.wap.wapp.core.data.repository.auth.AuthRepository
import com.wap.wapp.core.data.repository.auth.AuthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
abstract class AuthRepositoryModule {
    @Binds
    @ActivityScoped
    abstract fun providesAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository
}