package com.wap.wapp.core.commmon.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object DateUtil {

    fun generateNowTime(zoneId: ZoneId = ZoneId.of("Asia/Seoul")): LocalTime = LocalTime.now(zoneId)

    fun generateNowDate(zoneId: ZoneId = ZoneId.of("Asia/Seoul")): LocalDate = LocalDate.now(zoneId)

    fun generateNowDateTime(zoneId: ZoneId = ZoneId.of("Asia/Seoul")): LocalDateTime =
        LocalDateTime.now(zoneId)

    const val YEAR_MONTH_START_INDEX = 0
    const val YEAR_MONTH_END_INDEX = 7
    const val MONTH_DATE_START_INDEX = 5
    const val DAYS_IN_WEEK = 7

    // 현재 날짜에서 시간, 분만 반환해주는 포맷 ex 19:00
    val HHmmFormatter = DateTimeFormatter.ofPattern("HH:mm")

    // 현재 날짜에서 일만 반환해주는 포맷 ex 2023-11-20 -> 20
    val ddFormatter = DateTimeFormatter.ofPattern("dd")

    // 현재 날짜를 년-월-일 형식으로 반환해주는 포맷. ex 2023-11-20
    val yyyyMMddFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")

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
