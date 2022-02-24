package com.sedky.applicationtests.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_sleep_quality_table")
data class SleepNight(
    @PrimaryKey(autoGenerate = true)
    var nightId: Long = 0L,
    var startTimeMelli: Long = System.currentTimeMillis(),
    var endTimeMelli: Long = startTimeMelli,
    var sleepQuality: Int = -1
) {
}