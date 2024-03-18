package com.wap.wapp.core.data.repository.survey

import com.wap.wapp.core.data.utils.toISOLocalDateTimeString
import com.wap.wapp.core.model.survey.Survey
import com.wap.wapp.core.model.survey.SurveyAnswer
import com.wap.wapp.core.network.source.event.EventDataSource
import com.wap.wapp.core.network.source.survey.SurveyDataSource
import com.wap.wapp.core.network.source.user.UserDataSource
import java.time.LocalDateTime
import javax.inject.Inject

class SurveyRepositoryImpl @Inject constructor(
    private val surveyDataSource: SurveyDataSource,
    private val userDataSource: UserDataSource,
    private val eventDataSource: EventDataSource,
) : SurveyRepository {
    override suspend fun getSurveyList(): Result<List<Survey>> =
        surveyDataSource.getSurveyList().mapCatching { surveyList ->
            surveyList.map { surveyResponse ->
                userDataSource.getUserProfile(userId = surveyResponse.userId)
                    .mapCatching { userProfileResponse ->
                        val userName = userProfileResponse.toDomain().userName

                        eventDataSource.getEvent(eventId = surveyResponse.eventId)
                            .mapCatching { eventResponse ->
                                val eventName = eventResponse.toDomain().title

                                surveyResponse.toDomain(userName = userName, eventName = eventName)
                            }.getOrThrow()
                    }.getOrThrow()
            }
        }

    override suspend fun getSurveyListByEventId(eventId: String): Result<List<Survey>> =
        surveyDataSource.getSurveyListByEventId(eventId).mapCatching { surveyList ->
            surveyList.map { surveyResponse ->
                userDataSource.getUserProfile(userId = surveyResponse.userId)
                    .mapCatching { userProfileResponse ->
                        val userName = userProfileResponse.toDomain().userName

                        eventDataSource.getEvent(eventId = eventId)
                            .mapCatching { eventResponse ->
                                val eventName = eventResponse.toDomain().title

                                surveyResponse.toDomain(userName = userName, eventName = eventName)
                            }.getOrThrow()
                    }.getOrThrow()
            }
        }

    override suspend fun getSurveyListBySurveyFormId(surveyFormId: String): Result<List<Survey>> =
        surveyDataSource.getSurveyListByEventId(surveyFormId).mapCatching { surveyList ->
            surveyList.map { surveyResponse ->
                userDataSource.getUserProfile(userId = surveyResponse.userId)
                    .mapCatching { userProfileResponse ->
                        val userName = userProfileResponse.toDomain().userName

                        eventDataSource.getEvent(eventId = surveyResponse.eventId)
                            .mapCatching { eventResponse ->
                                val eventName = eventResponse.toDomain().title

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

                        eventDataSource.getEvent(eventId = surveyResponse.eventId)
                            .mapCatching { eventResponse ->
                                val eventName = eventResponse.toDomain().title

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

                    eventDataSource.getEvent(eventId = surveyResponse.eventId)
                        .mapCatching { eventResponse ->
                            val eventName = eventResponse.toDomain().title

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
}
