package com.martinlacorrona.helloarchitecture.presentation.view.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    fun formatDate(date: Date, pattern: String, locale: Locale = Locale.getDefault()): String =
        SimpleDateFormat(pattern, locale).format(date)
}