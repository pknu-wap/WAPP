package com.wap.wapp.core.network.utils

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

internal fun String.toISOLocalDateTime(): LocalDateTime = LocalDateTime.parse(
    this,
    DateTimeFormatter.ISO_LOCAL_DATE_TIME,
)

internal fun LocalDateTime.toISOLocalDateTimeString(): String =
    this.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)

internal fun generateNowDateTime(zoneId: ZoneId = ZoneId.of("Asia/Seoul")): LocalDateTime =
    LocalDateTime.now(zoneId)
