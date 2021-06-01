package com.android.byjusnewapp.helpers

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateHelper {
    private val FULL_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    private val PUBLISHER_DATE_FORMAT = "yyyy-MM-dd"



    fun getPublisherDateFromAPi(date: String) : String{

        try {
            val cal = Calendar.getInstance()
            val dateFormat = SimpleDateFormat(
                FULL_DATE_FORMAT, Locale.US
            )
            dateFormat.timeZone = TimeZone.getTimeZone("UTC");
            cal.time = dateFormat.parse(date)
            val expectedDateFormat = SimpleDateFormat(
                PUBLISHER_DATE_FORMAT, Locale.US
            )
            return expectedDateFormat.format(cal.time)
        }catch (e: ParseException){
            e.printStackTrace()
            return ""
        }
    }

}