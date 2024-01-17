package com.wap.wapp.core.data.repository.survey

import com.wap.wapp.core.data.utils.toISOLocalDateTimeString
import com.wap.wapp.core.model.survey.Survey
import com.wap.wapp.core.model.survey.SurveyAnswer
import com.wap.wapp.core.network.source.survey.SurveyDataSource
import com.wap.wapp.core.network.source.user.UserDataSource
import java.time.LocalDateTime
import javax.inject.Inject

class SurveyRepositoryImpl @Inject constructor(
    private val surveyDataSource: SurveyDataSource,
    private val userDataSource: UserDataSource,
) : SurveyRepository {
    override suspend fun getSurveyList(): Result<List<Survey>> =
        surveyDataSource.getSurveyList().mapCatching { surveyList ->
            surveyList.map { surveyResponse ->
                userDataSource.getUserProfile(userId = surveyResponse.userId)
                    .mapCatching { userProfileResponse ->
                        val userName = userProfileResponse.toDomain().userName

                        noticeNameResponse.mapCatching { noticeNameResponse ->
                            val eventName = noticeNameResponse.toDomain()

                            surveyResponse.toDomain(userName = userName, eventName = eventName)
                        }.getOrThrow()
                    }.getOrThrow()
            }
        }

    override suspend fun getSurveyList(eventId: String): Result<List<Survey>> =
        surveyDataSource.getSurveyList(eventId).mapCatching { surveyList ->
            surveyList.map { surveyResponse ->
                userDataSource.getUserProfile(userId = surveyResponse.userId)
                    .mapCatching { userProfileResponse ->
                        val userName = userProfileResponse.toDomain().userName

                        noticeNameResponse.mapCatching { noticeNameResponse ->
                            val eventName = noticeNameResponse.toDomain()

                            surveyResponse.toDomain(userName = userName, eventName = eventName)
                        }.getOrThrow()
                    }.getOrThrow()
            }
        }

    override suspend fun getUserRespondedSurveyList(userId: String): Result<List<Survey>> =
        surveyDataSource.getUserRespondedSurveyList(userId).mapCatching { surveyList ->
            surveyList.map { surveyResponse ->
                userDataSource.getUserProfile(userId = surveyResponse.userId)
                    .mapCatching { userProfileResponse ->
                        val userName = userProfileResponse.toDomain().userName

                        noticeNameResponse.mapCatching { noticeNameResponse ->
                            val eventName = noticeNameResponse.toDomain()

                            surveyResponse.toDomain(userName = userName, eventName = eventName)
                        }.getOrThrow()
                    }.getOrThrow()
            }
        }

    override suspend fun getSurvey(surveyId: String): Result<Survey> =
        surveyDataSource.getSurvey(surveyId).mapCatching { surveyResponse ->
            userDataSource.getUserProfile(userId = surveyResponse.userId)
                .mapCatching { userProfileResponse ->
                    val userName = userProfileResponse.toDomain().userName

                    noticeNameResponse.mapCatching { noticeNameResponse ->
                        val eventName = noticeNameResponse.toDomain()

                        surveyResponse.toDomain(userName = userName, eventName = eventName)
                    }.getOrThrow()
                }.getOrThrow()
        }

    override suspend fun deleteSurvey(surveyId: String): Result<Unit> =
        surveyDataSource.deleteSurvey(surveyId)

    override suspend fun postSurvey(
        surveyFormId: String,
        eventId: String,
        userId: String,
        title: String,
        content: String,
        surveyAnswerList: List<SurveyAnswer>,
        surveyedAt: LocalDateTime,
    ): Result<Unit> = surveyDataSource.postSurvey(
        surveyFormId = surveyFormId,
        eventId = eventId,
        userId = userId,
        title = title,
        content = content,
        surveyAnswerList = surveyAnswerList,
        surveyedAt = surveyedAt.toISOLocalDateTimeString(),
    )

    override suspend fun isSubmittedSurvey(surveyFormId: String, userId: String): Result<Boolean> =
        surveyDataSource.isSubmittedSurvey(surveyFormId, userId)

    private val noticeNameResponse: Result<String> = Result.success("notice datasource dummy data")

    // TODO 도메인 모델 구현을 위한 익스텐션, notice DataSource 구현 후 소거
    private fun String.toDomain(): String = this
}
