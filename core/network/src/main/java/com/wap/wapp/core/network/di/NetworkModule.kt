package com.wap.wapp.core.network.di

import com.wap.wapp.core.network.source.event.EventDataSource
import com.wap.wapp.core.network.source.event.EventDataSourceImpl
import com.wap.wapp.core.network.source.management.ManagementDataSource
import com.wap.wapp.core.network.source.management.ManagementDataSourceImpl
import com.wap.wapp.core.network.source.survey.SurveyDataSource
import com.wap.wapp.core.network.source.survey.SurveyDataSourceImpl
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
    abstract fun bindsManagementDataSource(
        managementDataSourceImpl: ManagementDataSourceImpl,
    ): ManagementDataSource

    @Binds
    @Singleton
    abstract fun bindsSurveyDataSoruce(
        surveyDataSourceImpl: SurveyDataSourceImpl,
    ): SurveyDataSource

    @Binds
    @Singleton
    abstract fun bindsEventDataSource(
        eventDataSourceImpl: EventDataSourceImpl,
    ): EventDataSource
}
