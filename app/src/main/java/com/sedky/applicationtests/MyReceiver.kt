package com.sedky.applicationtests

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class MyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        val isEnabled = intent?.getBooleanExtra("state", false) ?: return

        if(isEnabled){
            Toast.makeText(context, "isEnabled", Toast.LENGTH_SHORT).show()

        }else{
            Toast.makeText(context, "NotEnabled", Toast.LENGTH_SHORT).show()
        }


    }


}
