package com.example.covidmodule.common.utils

import java.text.SimpleDateFormat
import java.util.*

object CommonUtils {

    fun getFormattedDate(year: Int, month: Int, day: Int): String {
        val monthS: String = if(month<10) "0$month" else month.toString()
        val dayS: String = if(day<10) "0$day" else day.toString()
        return "$year-$monthS-$dayS"
    }

    fun getDateFormatted(date: String): String{
        return try {
            val formattedDateSplit = date.split("-")
            formattedDateSplit[0]+" de " + getMonthName(date) + " del " + formattedDateSplit[2]
        }catch (e: Exception){
            ""
        }

    }

    private fun getMonthName(date: String): String{
        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        cal.time = sdf.parse(date) as Date
        return SimpleDateFormat("MMMM", Locale("es", "ES")).format(cal.time)
    }

}