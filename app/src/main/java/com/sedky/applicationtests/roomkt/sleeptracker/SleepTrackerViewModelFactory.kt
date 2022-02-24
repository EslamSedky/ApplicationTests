package com.sedky.applicationtests.roomkt.sleeptracker

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sedky.applicationtests.persistence.SleepDataDao
import java.lang.IllegalArgumentException

class SleepTrackerViewModelFactory(
    private val dataSource: SleepDataDao,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    if(modelClass.isAssignableFrom(SleepTrackerViewModel::class.java)){
        return SleepTrackerViewModel(dataSource,application) as T
    }
        throw IllegalArgumentException("unknown viewModel class")
    }
}