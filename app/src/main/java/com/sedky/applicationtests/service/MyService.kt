package com.sedky.applicationtests.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log
import android.view.View
import com.sedky.applicationtests.R

class MyService : Service() {
    private lateinit var mediaPlayer: MediaPlayer
    private val TAG: String = "MySerrvice"

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }


    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "onCreate: ")
        mediaPlayer = MediaPlayer.create(baseContext, R.raw.yusof)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(TAG, "onStartCommand: ")
        mediaPlayer.start()

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
        Log.i(TAG, "onDestroy: ")
    }


}