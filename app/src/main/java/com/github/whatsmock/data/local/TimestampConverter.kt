@file:Suppress("unused")

package com.github.whatsmock.data.local

import androidx.room.TypeConverter
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class TimestampConverter {

    private val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)

    @TypeConverter
    fun toDate(value: String?): Date? {
        if (value == null) return null

        try {
            return df.parse(value)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return null
    }

    @TypeConverter
    fun toTimestamp(value: Date?): String? {
        if (value == null) return null

        return df.format(value)
    }
}