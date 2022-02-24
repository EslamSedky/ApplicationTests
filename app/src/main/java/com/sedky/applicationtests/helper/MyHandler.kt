package com.sedky.applicationtests.helper

import android.util.Log
import android.view.View
import com.sedky.applicationtests.data.model.Students

class MyHandler {
    val TAG: String = "MyHandlers"

    fun onClickToast(view: View){
        Log.i(TAG, "onClickToast: ")
    }
}