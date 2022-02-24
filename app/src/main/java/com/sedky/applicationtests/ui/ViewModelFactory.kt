package com.sedky.applicationtests.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sedky.applicationtests.data.api.ApiHelper
import com.sedky.applicationtests.data.repository.StationsRepository

class ViewModelFactory (val apiHelper : ApiHelper) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(StationsRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}