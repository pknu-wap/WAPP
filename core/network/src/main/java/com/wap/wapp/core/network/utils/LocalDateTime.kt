package com.wap.wapp.core.network.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

internal fun String.toISOLocalDateTime(): LocalDateTime = LocalDateTime.parse(
    this, DateTimeFormatter.ISO_LOCAL_DATE_TIME
)
