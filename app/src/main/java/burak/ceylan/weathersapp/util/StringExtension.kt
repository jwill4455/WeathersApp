package burak.ceylan.weathersapp.util

import burak.ceylan.weathersapp.util.StringExtension.formatDate
import java.text.SimpleDateFormat

object StringExtension {
   fun String.formatDate(patternOrigin: String = "yyy-MM-dd'T'HH:mm:ssZZZZZ", patternNeedFormat: String = "dd-MM-yyyy") : String {
       try {
           val format = SimpleDateFormat(patternOrigin)
           val d = format.parse(this)
           val format2 = SimpleDateFormat(patternNeedFormat)
           return format2.format(d)
       } catch (ex: Exception) {
           return this
       }
    }

    fun String.formatHour(patternOrigin: String = "yyy-MM-dd'T'HH:mm:ssZZZZZ", patternNeedFormat: String = "HH:mm"): String {
        try {
            val format = SimpleDateFormat(patternOrigin)
            val d = format.parse(this)
            val format2 = SimpleDateFormat(patternNeedFormat)
            return format2.format(d)
        }catch (ex: Exception) {
            return this
        }
    }
}