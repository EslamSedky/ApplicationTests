package com.sedky.applicationtests.roomkt.sleepdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sedky.applicationtests.persistence.SleepDataDao

class SleepDetailsViewModelFactory(
    private val sleepNightKey: Long,
    private val data: SleepDataDao
) : ViewModelProvider.Factory {
//    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SleepDetailsViewModel::class.java)) {
            return SleepDetailsViewModel(sleepNightKey, data) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }
}