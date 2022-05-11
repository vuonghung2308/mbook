package com.mh.mbook.db

import androidx.room.TypeConverter
import timber.log.Timber

object TypeConverters {
    @JvmStatic
    @TypeConverter
    fun stringToIntList(data: String?): List<Int>? {
        return data?.let { it ->
            it.split(",").map {
                try {
                    it.toInt()
                } catch (ex: NumberFormatException) {
                    Timber.e(ex, "Cannot convert $it to number")
                    null
                }
            }
        }?.filterNotNull()
    }

    @JvmStatic
    @TypeConverter
    fun intListToString(ints: List<Int>?): String? {
        return ints?.joinToString(",")
    }
}
