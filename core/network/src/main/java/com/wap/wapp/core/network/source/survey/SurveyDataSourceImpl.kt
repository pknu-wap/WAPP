package com.wap.wapp.core.network.source.survey

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.wap.wapp.core.model.survey.SurveyAnswer
import com.wap.wapp.core.network.constant.SURVEY_COLLECTION
import com.wap.wapp.core.network.model.survey.SurveyRequest
import com.wap.wapp.core.network.model.survey.SurveyResponse
import com.wap.wapp.core.network.utils.await
import javax.inject.Inject

class SurveyDataSourceImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
) : SurveyDataSource {
    override suspend fun getSurveyList(): Result<List<SurveyResponse>> = runCatching {
        val result: MutableList<SurveyResponse> = mutableListOf()

        val task = firebaseFirestore.collection(SURVEY_COLLECTION)
            .get()
            .await()

        for (document in task.documents) {
            val surveyResponse = document.toObject(SurveyResponse::class.java)
            checkNotNull(surveyResponse)

            result.add(surveyResponse)
        }

        result
    }

    override suspend fun getUserRespondedSurveyList(userId: String): Result<List<SurveyResponse>> =
        runCatching {
            val result: MutableList<SurveyResponse> = mutableListOf()

            val task = firebaseFirestore.collection(SURVEY_COLLECTION)
                .whereEqualTo("userId", userId)
                .get()
                .await()

            for (document in task.documents) {
                val surveyResponse = document.toObject(SurveyResponse::class.java)
                checkNotNull(surveyResponse)

                result.add(surveyResponse)
            }

            result
        }

    override suspend fun getSurvey(surveyId: String): Result<SurveyResponse> = runCatching {
        val result = firebaseFirestore.collection(SURVEY_COLLECTION)
            .document(surveyId)
            .get()
            .await()

        val surveyResponse = result.toObject(SurveyResponse::class.java)
        checkNotNull(surveyResponse)
    }

    override suspend fun deleteSurvey(surveyId: String): Result<Unit> = runCatching {
        firebaseFirestore.collection(SURVEY_COLLECTION)
            .document(surveyId)
            .delete()
            .await()
    }

    override suspend fun postSurvey(
        surveyFormId: String,
        eventId: String,
        userId: String,
        title: String,
        content: String,
        surveyAnswerList: List<SurveyAnswer>,
        surveyedAt: String,
    ): Result<Unit> = runCatching {
        val documentId = firebaseFirestore.collection(SURVEY_COLLECTION).document().id

        val surveyRequest = SurveyRequest(
            surveyId = documentId,
            surveyFormId = surveyFormId,
            eventId = eventId,
            userId = userId,
            title = title,
            content = content,
            surveyAnswerList = surveyAnswerList,
            surveyedAt = surveyedAt,
        )
        val setOption = SetOptions.merge()

        firebaseFirestore.collection(SURVEY_COLLECTION)
            .document(documentId)
            .set(surveyRequest, setOption)
            .await()
    }

    override suspend fun isSubmittedSurvey(
        surveyFormId: String,
        userId: String,
    ): Result<Boolean> = runCatching {
        val result = firebaseFirestore.collection(SURVEY_COLLECTION)
            .whereEqualTo("surveyFormId", surveyFormId)
            .whereEqualTo("userId", userId)
            .get()
            .await()

        result.isEmpty.not()
    }
}
