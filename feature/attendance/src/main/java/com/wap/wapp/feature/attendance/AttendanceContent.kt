package com.wap.wapp.feature.attendance

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.wap.wapp.feature.attendance.component.AttendanceItemCard
import com.wap.wapp.feature.attendance.model.EventAttendanceStatus

@Composable
internal fun AttendanceContent(
    eventsAttendanceStatus: List<EventAttendanceStatus>,
    onSelectEventId: (String) -> Unit,
    onSelectEventTitle: (String) -> Unit,
    setAttendanceDialog: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        items(items = eventsAttendanceStatus, key = { it.eventId }) { attendanceStatus ->
            AttendanceItemCard(
                eventTitle = attendanceStatus.title,
                eventContent = attendanceStatus.content,
                remainAttendanceDateTime =
                attendanceStatus.remainAttendanceDateTime,
                isAttendance = attendanceStatus.isAttendance,
                onSelectItemCard = {
                    onSelectEventId(attendanceStatus.eventId)
                    onSelectEventTitle(attendanceStatus.title)
                    setAttendanceDialog()
                },
            )
        }
    }
}
