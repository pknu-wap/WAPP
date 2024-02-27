package com.wap.wapp.core.network.di

import com.wap.wapp.core.network.source.attendance.AttendanceDataSource
import com.wap.wapp.core.network.source.attendance.AttendanceDataSourceImpl
import com.wap.wapp.core.network.source.attendancestatus.AttendanceStatusDataSource
import com.wap.wapp.core.network.source.attendancestatus.AttendanceStatusDataSourceImpl
import com.wap.wapp.core.network.source.auth.AuthDataSource
import com.wap.wapp.core.network.source.auth.AuthDataSourceImpl
import com.wap.wapp.core.network.source.auth.SignUpDataSource
import com.wap.wapp.core.network.source.auth.SignUpDataSourceImpl
import com.wap.wapp.core.network.source.event.EventDataSource
import com.wap.wapp.core.network.source.event.EventDataSourceImpl
import com.wap.wapp.core.network.source.management.ManagementDataSource
import com.wap.wapp.core.network.source.management.ManagementDataSourceImpl
import com.wap.wapp.core.network.source.survey.SurveyDataSource
import com.wap.wapp.core.network.source.survey.SurveyDataSourceImpl
import com.wap.wapp.core.network.source.survey.SurveyFormDataSource
import com.wap.wapp.core.network.source.survey.SurveyFormDataSourceImpl
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
    abstract fun bindsAuthDataSource(
        authDataSourceImpl: AuthDataSourceImpl,
    ): AuthDataSource

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
    abstract fun bindsSurveyFormDateSource(
        surveyFormDataSourceImpl: SurveyFormDataSourceImpl,
    ): SurveyFormDataSource

    @Binds
    @Singleton
    abstract fun bindsEventDataSource(
        eventDataSourceImpl: EventDataSourceImpl,
    ): EventDataSource

    @Binds
    @Singleton
    abstract fun bindsAttendanceDataSource(
        attendanceDataSourceImpl: AttendanceDataSourceImpl,
    ): AttendanceDataSource

    @Binds
    @Singleton
    abstract fun bindsAttendanceStatusDataSource(
        attendanceStatueDataSourceImpl: AttendanceStatusDataSourceImpl,
    ): AttendanceStatusDataSource

    @Binds
    @Singleton
    abstract fun bindsSignUpDataSource(
        signUpDataSourceImpl: SignUpDataSourceImpl,
    ): SignUpDataSource
}
