package com.zain.newslistswadaya.utils

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

object DateFormatUtils {
    fun formatDate(isoDate: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")

        val outputFormat = SimpleDateFormat("EEE, dd MMMM HH.mm", Locale("id", "ID"))

        val date = inputFormat.parse(isoDate)
        return outputFormat.format(date!!)
    }
}