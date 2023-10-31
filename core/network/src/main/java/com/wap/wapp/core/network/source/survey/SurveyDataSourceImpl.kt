package com.wap.wapp.core.network.source.survey

import com.google.firebase.firestore.FirebaseFirestore
import com.wap.wapp.core.network.constant.SURVEY_COLLECTION
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
}
