package com.wap.wapp.core.data.di

import com.wap.wapp.core.data.repository.attendance.AttendanceRepository
import com.wap.wapp.core.data.repository.attendance.AttendanceRepositoryImpl
import com.wap.wapp.core.data.repository.auth.AuthRepository
import com.wap.wapp.core.data.repository.auth.AuthRepositoryImpl
import com.wap.wapp.core.data.repository.event.EventRepository
import com.wap.wapp.core.data.repository.event.EventRepositoryImpl
import com.wap.wapp.core.data.repository.management.ManagementRepository
import com.wap.wapp.core.data.repository.management.ManagementRepositoryImpl
import com.wap.wapp.core.data.repository.survey.SurveyFormRepository
import com.wap.wapp.core.data.repository.survey.SurveyFormRepositoryImpl
import com.wap.wapp.core.data.repository.survey.SurveyRepository
import com.wap.wapp.core.data.repository.survey.SurveyRepositoryImpl
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
    abstract fun bindsAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl,
    ): AuthRepository

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

    @Binds
    @Singleton
    abstract fun bindsSurveyRepository(
        surveyRepositoryImpl: SurveyRepositoryImpl,
    ): SurveyRepository

    @Binds
    @Singleton
    abstract fun bindsSurveyFormRepository(
        surveyFormRepositoryImpl: SurveyFormRepositoryImpl,
    ): SurveyFormRepository

    @Binds
    @Singleton
    abstract fun bindsEventRepository(
        eventRepositoryImpl: EventRepositoryImpl,
    ): EventRepository

    @Binds
    @Singleton
    abstract fun bindsAttendanceRepository(
        attendanceRepositoryImpl: AttendanceRepositoryImpl,
    ): AttendanceRepository
}
