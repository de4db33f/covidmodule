package com.example.covidmodule.common.utils

import java.text.SimpleDateFormat
import java.util.*

object CommonUtils {

    fun getFullDate(epoch: Long): String = getFormattedTime(epoch)

    private fun getFormattedTime(epoch: Long): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val netDate = Date(epoch)
        return sdf.format(netDate)
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
        return SimpleDateFormat("MMMM", Locale.getDefault()).format(cal.time)

    }

}