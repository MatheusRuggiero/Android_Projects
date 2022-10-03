package br.com.tecnomotor.thanos.util

import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*

class DateUtil {
    companion object {
        fun strToDate(aDate: String, aFormat: String): Date {
            val pos = ParsePosition(0)
            val simpledateformat = SimpleDateFormat(aFormat)
            return simpledateformat.parse(aDate, pos)!!
        }

        fun dateToStr(aDate: Date, aFormat: String): String {
            val simpledateformat = SimpleDateFormat(aFormat)
            return simpledateformat.format(aDate)
        }
    }
}