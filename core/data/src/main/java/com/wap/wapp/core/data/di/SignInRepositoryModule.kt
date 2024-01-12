package com.wap.wapp.core.data.di

import com.wap.wapp.core.data.repository.auth.SignInRepository
import com.wap.wapp.core.data.repository.auth.SignInRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
abstract class SignInRepositoryModule {
    @Binds
    @ActivityScoped
    abstract fun bindsAuthRepository(
        signInRepositoryImpl: SignInRepositoryImpl,
    ): SignInRepository
}
