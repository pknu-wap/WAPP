package com.wap.wapp.core.network.source.survey

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.wap.wapp.core.network.constant.SURVEY_FORM_COLLECTION
import com.wap.wapp.core.network.model.survey.form.SurveyFormRequest
import com.wap.wapp.core.network.model.survey.form.SurveyFormResponse
import com.wap.wapp.core.network.utils.await
import javax.inject.Inject

class SurveyFormDataSourceImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
) : SurveyFormDataSource {
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

    override suspend fun postSurveyForm(
        surveyFormRequest: SurveyFormRequest,
    ): Result<Unit> {
        return runCatching {
            val setOption = SetOptions.merge()
            firebaseFirestore.collection(SURVEY_FORM_COLLECTION)
                .document(surveyFormRequest.eventId.toString())
                .set(surveyFormRequest, setOption)
                .await()
        }
    }
}
