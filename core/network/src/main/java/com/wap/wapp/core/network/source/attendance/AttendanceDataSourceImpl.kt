package com.wap.wapp.core.network.source.attendance

import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class AttendanceDataSourceImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
) : AttendanceDataSource
