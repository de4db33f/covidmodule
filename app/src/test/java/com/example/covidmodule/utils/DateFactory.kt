package com.example.covidmodule.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class DateFactory {
    fun getDateToApi(): String {
        val today = LocalDate.now().minusDays(1)
        return today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    }

    fun getTodayDate(): String {
        val today = LocalDate.now()
        return today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    }

    fun getTomorrowDate(): String {
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        var dt = Date()
        val c: Calendar = Calendar.getInstance()
        c.time = dt
        c.add(Calendar.DATE, 1)
        dt = c.time
        return dateFormat.format(dt.time)
    }
}