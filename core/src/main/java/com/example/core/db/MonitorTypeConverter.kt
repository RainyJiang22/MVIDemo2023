package com.example.core.db

import androidx.room.TypeConverter
import com.example.core.util.JsonFormat

/**
 * @author jiangshiyu
 * @date 2023/12/4
 */

internal class MonitorTypeConverter {

    @TypeConverter
    fun fromJsonArray(json: String): List<MonitorPair> {
        return JsonFormat.fromJsonArray(json, MonitorPair::class.java)
    }

    @TypeConverter
    fun toJson(list: List<MonitorPair>): String {
        return JsonFormat.toJson(list)
    }
}