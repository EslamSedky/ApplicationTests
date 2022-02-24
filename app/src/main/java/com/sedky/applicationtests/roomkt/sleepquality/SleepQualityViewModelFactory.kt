package com.sedky.applicationtests.roomkt.sleepquality

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sedky.applicationtests.persistence.SleepDataDao
import java.lang.IllegalArgumentException

class SleepQualityViewModelFactory (
    private val sleepKey : Long ,
    private val database : SleepDataDao
        ) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SleepQualityViewModel::class.java)){
            return SleepQualityViewModel(sleepKey,database) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }
}