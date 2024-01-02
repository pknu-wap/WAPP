package com.wap.wapp.core.data.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

internal fun LocalDateTime.toISOLocalDateTimeString(): String =
    this.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
