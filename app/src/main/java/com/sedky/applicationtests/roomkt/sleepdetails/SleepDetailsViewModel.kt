package com.sedky.applicationtests.roomkt.sleepdetails

import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sedky.applicationtests.data.model.SleepNight
import com.sedky.applicationtests.persistence.SleepDataDao

class SleepDetailsViewModel(
    private val sleepNightKey: Long,
    private val data: SleepDataDao
) : ViewModel() {

    private val TAG: String = "SleepDetailsViewModel"
    val database = data
    private val night = MediatorLiveData<SleepNight>()

    fun getNight() = night

    init {
        night.addSource(database.getNightWithId(sleepNightKey), night::setValue)
        Log.i(TAG, " $sleepNightKey")
        Log.i(TAG, "night: $night")
    }

    private val _navigateToSleepTracker = MutableLiveData<Boolean?>()

    val navigateToSleepTracker
        get() = _navigateToSleepTracker

    fun doneNavigating() {
        _navigateToSleepTracker.value = null
    }

    fun onClose() {
        _navigateToSleepTracker.value = true
    }
}