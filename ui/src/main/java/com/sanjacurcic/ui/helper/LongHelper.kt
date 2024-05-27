package com.sanjacurcic.ui.helper

fun Long.getMinutesAndSecondsString(): String {
    val minutes = (this % 3600) / 60
    val seconds = this % 60
    val min = if (minutes < 10) "0$minutes" else "$minutes"
    val sec = if (seconds < 10) "0$seconds" else "$seconds"
    return "$min:$sec"
}