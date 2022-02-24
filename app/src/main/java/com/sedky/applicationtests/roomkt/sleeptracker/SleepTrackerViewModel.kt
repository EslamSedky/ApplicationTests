package com.sedky.applicationtests.roomkt.sleeptracker

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.sedky.applicationtests.data.model.SleepNight
import com.sedky.applicationtests.persistence.SleepDataDao
import com.sedky.applicationtests.utils.formatNights
import kotlinx.coroutines.*

class SleepTrackerViewModel(
    private val databaseDao: SleepDataDao,
    application: Application
) : AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val TAG = "SleepTrackerViewModel"


    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val tonight = MutableLiveData<SleepNight?>()
    var nights = databaseDao.getAllNights()
    var nightsString = Transformations.map(nights) { nights ->
        formatNights(nights, application.resources)
    }

    val startButtonVisible = Transformations.map(tonight) {
        null == it
    }
    val stopButtonVisible = Transformations.map(tonight) {
        null != it
    }
    val clearButtonVisible = Transformations.map(nights) {
        it?.isNotEmpty()
    }

    private val _navigateToSleepQuality = MutableLiveData<SleepNight>()
    val navigateToSleepNightQuality: LiveData<SleepNight>
        get() = _navigateToSleepQuality

    fun doneNavigating() {
        _navigateToSleepQuality.value = null
    }

    private val _showSnackBarEvent = MutableLiveData<Boolean>()
    val showSnackBarEvent: LiveData<Boolean>
        get() = _showSnackBarEvent

    init {
        initializeTonight()

    }

    private fun initializeTonight() {
        uiScope.launch {
            tonight.value = getTonightFromDatabase()
        }
    }

    private suspend fun getTonightFromDatabase(): SleepNight? {
        return withContext(Dispatchers.IO) {
            var night = databaseDao.getTonight()
            if (night?.endTimeMelli != night?.startTimeMelli) {
                night = null
            }
            Log.i(TAG, "getTonightFromDatabase: $night")
            night
        }

    }

    fun onStartTracking() {
        uiScope.launch {
            val newNight = SleepNight()
            insert(newNight)
            tonight.value = getTonightFromDatabase() //important   <<-----------
        }
    }

    private suspend fun insert(newNight: SleepNight) {
        withContext(Dispatchers.IO) {
            databaseDao.insert(newNight)
        }
    }

    fun onStopTracking() {
        uiScope.launch {
            var oldNight = tonight.value ?: return@launch
            oldNight.endTimeMelli = System.currentTimeMillis()
            update(oldNight)
            _navigateToSleepQuality.value = oldNight

        }
    }

    private suspend fun update(oldNight: SleepNight) {
        withContext(Dispatchers.IO) {
            databaseDao.update(oldNight)
        }
    }

    fun onClear() {
        uiScope.launch {
            clear()
            tonight.value = null
            _showSnackBarEvent.value = true
        }
    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            databaseDao.clear()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun doneShowingSnackBar() {
        _showSnackBarEvent.value = false
    }

    private val _navigateToSleepDataQuality = MutableLiveData<Long>()
    val navigateToSleepDataQuality
        get() = _navigateToSleepDataQuality

    fun onSleepNightClicked(id: Long) {
        _navigateToSleepDataQuality.value = id
    }

    fun onSleepDataQualityNavigated() {
        _navigateToSleepDataQuality.value = null
    }

    private val _navigateToSleepDetails = MutableLiveData<Long>()
    val navigateToSleepDetails
        get() = _navigateToSleepDetails

    fun onSleepDetailsNavigated(){
        _navigateToSleepDetails.value = null
    }
}