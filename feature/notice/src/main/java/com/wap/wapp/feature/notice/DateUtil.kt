package com.wap.wapp.feature.notice

import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject

class DateUtil @Inject constructor() {

    private val seoulZoneId = ZoneId.of("Asia/Seoul")

    // 현재 시간을 Pair<2023-11-20, "월요일"> 과 같은 쌍으로 반환합니다.
    fun generateNowDateAndDay(date: LocalDate = LocalDate.now(seoulZoneId)): Pair<String, String> {
        val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
        val dayOfWeek = date.dayOfWeek

        return formatter.format(date) to dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN)
    }

    companion object {
        const val YEAR_MONTH_START_INDEX = 0
        const val YEAR_MONTH_END_INDEX = 7
        const val MONTH_DATE_START_INDEX = 5
    }

    enum class DaysOfWeek(val displayName: String) {
        SUNDAY("일"),
        MONDAY("월"),
        TUESDAY("화"),
        WEDNESDAY("수"),
        THURSDAY("목"),
        FRIDAY("금"),
        SATURDAY("토"),
    }
}
