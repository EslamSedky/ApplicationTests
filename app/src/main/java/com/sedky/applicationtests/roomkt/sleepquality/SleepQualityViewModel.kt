package com.sedky.applicationtests.roomkt.sleepquality

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sedky.applicationtests.persistence.SleepDataDao
import kotlinx.coroutines.*

class SleepQualityViewModel(
    private val sleepKey: Long = 0L,
    private val database: SleepDataDao
) : ViewModel() {

    private val viewModeJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModeJob)
    private val _navigateToSleepTracker = MutableLiveData<Boolean?>()

    val navigateToSleepTracker: LiveData<Boolean?>
        get() = _navigateToSleepTracker

    fun doneNavigating() {
        _navigateToSleepTracker.value = null
    }

    fun onSetSleepQuality(quality: Int) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                val tonight = database.get(sleepKey) ?: return@withContext
                tonight.sleepQuality = quality
                database.update(tonight)
            }
            _navigateToSleepTracker.value = true
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModeJob.cancel()
    }
}