package com.wap.wapp.core.network.source.survey

import com.google.firebase.firestore.FirebaseFirestore
import com.wap.wapp.core.network.constant.SURVEY_COLLECTION
import com.wap.wapp.core.network.constant.SURVEY_FORM_COLLECTION
import com.wap.wapp.core.network.model.survey.SurveyFormResponse
import com.wap.wapp.core.network.model.survey.SurveyResponse
import com.wap.wapp.core.network.utils.await
import javax.inject.Inject

class SurveyDataSourceImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
) : SurveyDataSource {
    override suspend fun getSurveyList(): Result<List<SurveyResponse>> {
        return runCatching {
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
    }

    override suspend fun getSurvey(surveyId: String): Result<SurveyResponse> {
        return runCatching {
            val result = firebaseFirestore.collection(SURVEY_COLLECTION)
                .document(surveyId)
                .get()
                .await()

            val surveyResponse = result.toObject(SurveyResponse::class.java)
            checkNotNull(surveyResponse)
        }
    }

    override suspend fun getSurveyFormList(): Result<List<SurveyFormResponse>> {
        return runCatching {
            val result: MutableList<SurveyFormResponse> = mutableListOf()

            val task = firebaseFirestore.collection(SURVEY_FORM_COLLECTION)
                .get()
                .await()

            for (document in task.documents) {
                val surveyFormResponse = document.toObject(SurveyFormResponse::class.java)
                checkNotNull(surveyFormResponse)

                result.add(surveyFormResponse)
            }

            result
        }
    }

    override suspend fun getSurveyForm(eventId: Int): Result<SurveyFormResponse> {
        return runCatching {
            val result = firebaseFirestore.collection(SURVEY_FORM_COLLECTION)
                .document(eventId.toString())
                .get()
                .await()

            val surveyFormResponse = result.toObject(SurveyFormResponse::class.java)
            checkNotNull(surveyFormResponse)
        }
    }
}
