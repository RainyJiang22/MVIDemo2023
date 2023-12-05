package com.example.core.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.core.util.ContextProvider

/**
 * @author jiangshiyu
 * @date 2023/12/4
 */
@Database(entities = [Monitor::class], version = 1)
@TypeConverters(MonitorTypeConverter::class)
internal abstract class MonitorDataBase : RoomDatabase() {

    abstract val monitorDao: MonitorDao

    companion object {

        private const val DB_NAME = "Monitor"

        const val MonitorTableName = "MonitorHttp"

        private var monitorDataBase: MonitorDataBase? = null


        val instance: MonitorDataBase
            get() {
                return monitorDataBase ?: synchronized(lock = MonitorDataBase::class.java) {
                    val cache = monitorDataBase
                    if (cache != null) {
                        return@synchronized cache
                    }
                    val db = createDb(context = ContextProvider.context)
                    monitorDataBase = db
                    return@synchronized db
                }
            }

        private fun createDb(context: Context): MonitorDataBase {
            return Room.databaseBuilder(
                context,
                MonitorDataBase::class.java,
                DB_NAME,
            ).fallbackToDestructiveMigration()
                .build()
        }
    }
}