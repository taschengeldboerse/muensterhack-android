package de.muensterhack.ext

import java.text.SimpleDateFormat
import java.util.Locale

private const val DATE_FORMAT_API = "dd-MM-yyyy"
private const val DATE_FORMAT_APP = "dd. MM. yyyy"

fun String.formatDate(): String {
    val parsedDate = SimpleDateFormat(DATE_FORMAT_API, Locale.getDefault()).parse(this)
    return SimpleDateFormat(DATE_FORMAT_APP, Locale.getDefault()).format(parsedDate)
}