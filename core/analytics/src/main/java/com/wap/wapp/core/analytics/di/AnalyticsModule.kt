package com.wap.wapp.core.analytics.di

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.wap.wapp.core.analytics.AnalyticsHelper
import com.wap.wapp.core.analytics.AnalyticsHelperImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AnalyticsModule {
    @Binds
    @Singleton
    abstract fun bindsAnalyticsHelper(analyticsHelperImpl: AnalyticsHelperImpl): AnalyticsHelper

    companion object {
        @Provides
        @Singleton
        fun provideFirebaseAnalytics(): FirebaseAnalytics {
            return Firebase.analytics
        }
    }
}
