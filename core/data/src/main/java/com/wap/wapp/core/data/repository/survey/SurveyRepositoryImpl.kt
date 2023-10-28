package com.wap.wapp.core.data.repository.survey

import com.wap.wapp.core.model.survey.Survey
import com.wap.wapp.core.network.source.survey.SurveyDataSource
import com.wap.wapp.core.network.source.user.UserDataSource
import javax.inject.Inject

class SurveyRepositoryImpl @Inject constructor(
    private val surveyDataSource: SurveyDataSource,
    private val userDataSource: UserDataSource,
) : SurveyRepository {
    override suspend fun getSurveyList(): Result<List<Survey>> {
        return surveyDataSource.getSurveyList().mapCatching { surveyList ->
            surveyList.map { surveyResponse ->
                userDataSource.getUserProfile(userId = surveyResponse.userId)
                    .mapCatching { userProfileResponse ->
                        val userName = userProfileResponse.toDomain().userName

                        noticeNameResponse.mapCatching { noticeNameResponse ->
                            val noticeName = noticeNameResponse.toDomain()

                            surveyResponse.toDomain(userName = userName, noticeName = noticeName)
                        }.getOrThrow()
                    }.getOrThrow()
            }
        }
    }

    private val noticeNameResponse: Result<String> = Result.success("notice datasource dummy data")

    // TODO 도메인 모델 구현을 위한 익스텐션, notice DataSource 구현 후 소거
    private fun String.toDomain(): String = this
}
