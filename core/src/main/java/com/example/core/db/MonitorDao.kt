package com.example.core.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * @author jiangshiyu
 * @date 2023/12/4
 */

@Dao
internal interface MonitorDao {


    @Insert
    fun insert(monitor: Monitor): Long

    @Update
    fun update(monitor: Monitor)

    @Query("select * from ${MonitorDataBase.MonitorTableName} where id =:id")
    suspend fun query(id: Long): Monitor

    @Query("select * from ${MonitorDataBase.MonitorTableName} where id =:id")
    fun queryFlow(id: Long): Flow<Monitor>

    @Query("select * from ${MonitorDataBase.MonitorTableName} order by id desc limit :limit")
    fun queryFlow(limit: Int): Flow<List<Monitor>>

    @Query("delete from ${MonitorDataBase.MonitorTableName}")
    suspend fun deleteAll()


}