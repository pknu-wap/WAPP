package com.wap.wapp.core.network.source.survey

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.wap.wapp.core.model.survey.SurveyQuestion
import com.wap.wapp.core.network.constant.SURVEY_FORM_COLLECTION
import com.wap.wapp.core.network.model.survey.form.SurveyFormRequest
import com.wap.wapp.core.network.model.survey.form.SurveyFormResponse
import com.wap.wapp.core.network.utils.await
import javax.inject.Inject

class SurveyFormDataSourceImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
) : SurveyFormDataSource {
    override suspend fun getSurveyForm(surveyFormId: String): Result<SurveyFormResponse> =
        runCatching {
            val result = firebaseFirestore.collection(SURVEY_FORM_COLLECTION)
                .document(surveyFormId)
                .get()
                .await()

            val surveyFormResponse = result.toObject(SurveyFormResponse::class.java)
            checkNotNull(surveyFormResponse)
        }

    override suspend fun getSurveyFormList(): Result<List<SurveyFormResponse>> = runCatching {
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

    override suspend fun getSurveyFormListByEventId(
        eventId: String,
    ): Result<List<SurveyFormResponse>> = runCatching {
        val result: MutableList<SurveyFormResponse> = mutableListOf()

        val task = firebaseFirestore.collection(SURVEY_FORM_COLLECTION)
            .whereEqualTo("eventId", eventId)
            .get()
            .await()

        for (document in task.documents) {
            val surveyFormResponse = document.toObject(SurveyFormResponse::class.java)
            checkNotNull(surveyFormResponse)

            result.add(surveyFormResponse)
        }

        result
    }

    override suspend fun deleteSurveyForm(surveyFormId: String): Result<Unit> = runCatching {
        firebaseFirestore.collection(SURVEY_FORM_COLLECTION)
            .document(surveyFormId)
            .delete()
            .await()
    }

    override suspend fun postSurveyForm(
        eventId: String,
        title: String,
        content: String,
        surveyQuestionList: List<SurveyQuestion>,
        deadline: String,
    ): Result<Unit> = runCatching {
        val documentId = firebaseFirestore.collection(SURVEY_FORM_COLLECTION).document().id
        val surveyFormRequest = SurveyFormRequest(
            surveyFormId = documentId,
            eventId = eventId,
            title = title,
            content = content,
            surveyQuestionList = surveyQuestionList,
            deadline = deadline,
        )

        val setOption = SetOptions.merge()
        firebaseFirestore.collection(SURVEY_FORM_COLLECTION)
            .document(documentId)
            .set(surveyFormRequest, setOption)
            .await()
    }

    override suspend fun updateSurveyForm(surveyFormRequest: SurveyFormRequest): Result<Unit> =
        runCatching {
            val surveyFormMap = mapOf(
                "surveyFormId" to surveyFormRequest.surveyFormId,
                "eventId" to surveyFormRequest.eventId,
                "title" to surveyFormRequest.title,
                "content" to surveyFormRequest.content,
                "surveyQuestionList" to surveyFormRequest.surveyQuestionList,
                "deadline" to surveyFormRequest.deadline,
            )

            firebaseFirestore.collection(SURVEY_FORM_COLLECTION)
                .document(surveyFormRequest.surveyFormId)
                .update(surveyFormMap)
                .await()
        }
}
