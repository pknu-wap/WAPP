package com.wap.wapp.core.commmon.extensions

import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestoreException
import java.net.UnknownHostException

fun Throwable.toSupportingText(): String {
    return when (this) {
        is UnknownHostException -> "네트워크 연결이 원활하지 않습니다."

        is FirebaseAuthException -> this.toSupportingText()

        is FirebaseFirestoreException -> this.toSupportingText()

        is IllegalStateException -> this.message.toString()

        else -> "알 수 없는 오류가 발생하였습니다."
    }
}

fun FirebaseAuthException.toSupportingText(): String {
    return when (this.errorCode) {
        "ERROR_WEB_CONTEXT_CANCELED", "ERROR_USER_CANCELLED" -> "다시 시도해 주세요."

        else -> "알 수 없는 오류가 발생하였습니다."
    }
}

fun FirebaseFirestoreException.toSupportingText(): String {
    return when (this.code.value()) {
        7 -> "접근 권한이 없습니다."

        16 -> "회원이 만료되었습니다. 다시 로그인 해주세요."

        else -> "알 수 없는 오류가 발생하였습니다."
    }
}
