package com.sedky.applicationtests.ui.home

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    init{


    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }

    val text: LiveData<String> = _text


}