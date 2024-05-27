package com.sanjacurcic.domain.helper

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun Instant.getFormattedTime(pattern: String): String {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return this.atZone(ZoneId.systemDefault()).toLocalTime().format(formatter)
}

fun Instant.getFormattedDate(pattern: String): String {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return this.atZone(ZoneId.systemDefault()).toLocalDateTime().format(formatter)
}