package com.sedky.applicationtests.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.sedky.applicationtests.data.repository.StationsRepository
import com.sedky.applicationtests.utils.Resource
import kotlinx.coroutines.Dispatchers


class MainViewModel(private val mainRepository : StationsRepository ) : ViewModel(){

    fun getStation() = liveData(Dispatchers.IO){
        emit(Resource.loading(null))

        try {
            emit(Resource.success(data = mainRepository.getStations()))
        } catch (e: Exception) {
            emit(Resource.error(null,"Opps..error occurred"))
        }
    }
}