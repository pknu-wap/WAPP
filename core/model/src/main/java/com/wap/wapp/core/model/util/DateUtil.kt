package com.wap.wapp.core.model.util

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

    // 현재 날짜에서 시간, 분만 반환해주는 포맷 ex 19:00
    val HHmmFormatter = DateTimeFormatter.ofPattern("HH:mm")

    // 현재 날짜에서 일만 반환해주는 포맷 ex 2023-11-20 -> 20
    val ddFormatter = DateTimeFormatter.ofPattern("dd")

    // 현재 날짜를 년-월-일 형식으로 반환해주는 포맷. ex 2023-11-20
    val yyyyMMddFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")

    // 현재 날짜를 월-일 형식으로 반환해주는 포맷. ex 11월 20일
    val MMddFormatter = DateTimeFormatter.ofPattern("MM월 dd일")
}
