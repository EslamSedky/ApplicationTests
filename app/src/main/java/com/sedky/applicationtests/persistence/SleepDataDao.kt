package com.sedky.applicationtests.persistence

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.sedky.applicationtests.data.model.SleepNight

@Dao
interface SleepDataDao {

    @Insert
    fun insert(night: SleepNight)

    @Update
    fun update(night: SleepNight)

    @Query("SELECT * FROM daily_sleep_quality_table WHERE nightId = :key")
    fun get(key : Long) : SleepNight

    @Query("DELETE FROM daily_sleep_quality_table")
    fun clear()

    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC")
    fun getAllNights() :LiveData<List<SleepNight>>

    @Query("SELECT * FROM DAILY_SLEEP_QUALITY_TABLE ORDER BY nightId DESC LIMIT 1")
    fun getTonight() : SleepNight?

    @Query("SELECT * FROM DAILY_SLEEP_QUALITY_TABLE WHERE nightId = :key")
    fun getNightWithId(key : Long) : LiveData<SleepNight>
}